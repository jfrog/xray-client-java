package com.jfrog.xray.client.impl.services.graph;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jfrog.xray.client.impl.XrayClient;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.graph.Graph;
import com.jfrog.xray.client.services.graph.GraphResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jfrog.build.extractor.scan.DependencyTree;

import java.io.IOException;

/**
 * Created by tala on 9/30/21.
 */
public class GraphImpl implements Graph {

    private static final ObjectMapper mapper = createFilteredObjectMapper();
    private static final int MAX_ATTEMPTS = 60;
    private static final int SYNC_SLEEP_INTERVAL = 5000;
    private final XrayClient xray;

    public GraphImpl(XrayClient xray) {
        this.xray = xray;
    }

    public static ObjectMapper createFilteredObjectMapper() {
        ObjectMapper mapper = ObjectMapperHelper.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false)
                .addFilter("xray-graph-filter", SimpleBeanPropertyFilter.filterOutAllExcept("component_id", "nodes"));
        mapper.setFilterProvider(filters);
        return mapper;
    }

    @Override
    public GraphResponse graph(DependencyTree dependencies, Runnable checkCanceled, String projectKey) throws IOException, InterruptedException {
        if (projectKey == null || projectKey.isEmpty()) {
            return graph(dependencies, checkCanceled);
        }
        if (dependencies == null) {
            return new GraphResponseImpl();
        }
        return this.post("?project=" + projectKey, dependencies, checkCanceled);
    }

    @Override
    public GraphResponse graph(DependencyTree dependencies, Runnable checkCanceled) throws IOException, InterruptedException {
        if (dependencies == null) {
            return new GraphResponseImpl();
        }
        return this.post("", dependencies, checkCanceled);
    }

    private GraphResponse post(String params, Object body, Runnable checkCanceled) throws IOException, InterruptedException {
        HttpEntity entity = null;
        // First, request a scan from Xray.
        try (CloseableHttpResponse response = xray.post("scan/graph" + params, body, mapper)) {
            checkCanceled.run();
            entity = response.getEntity();
            GraphResponse requestResponse = mapper.readValue(response.getEntity().getContent(), GraphResponseImpl.class);
            String scanId = requestResponse.getScanId();
            // If no context was provided (project name), we would like to receive all known vulnerabilities.
            String includeVulnerabilities = !params.isEmpty() ? "" : "&include_vulnerabilities=true";
            return getGraphScanResults(scanId, includeVulnerabilities, checkCanceled);

        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    private GraphResponse getGraphScanResults(String scanId, String includeVulnerabilities, Runnable checkCanceled) throws IOException, InterruptedException {
        HttpEntity entity = null;
        // Xray will respond with 201 until the completion of the scan. Once completed, 200 will be returned.
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try (CloseableHttpResponse res = xray.get("scan/graph/" + scanId + "?include_licenses=true" + includeVulnerabilities)) {
                checkCanceled.run();
                StatusLine statusLine = res.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    // We got an answer (scan completed), return it.
                    entity = res.getEntity();
                    return mapper.readValue(res.getEntity().getContent(), GraphResponseImpl.class);
                }
            } finally {
                EntityUtils.consumeQuietly(entity);
            }
            // Wait between polling attempts.
            Thread.sleep(SYNC_SLEEP_INTERVAL);
        }
        // Will get here only in case of timeout.
        throw new IOException("Xray get graph scan exceeded the timeout.");
    }
}
