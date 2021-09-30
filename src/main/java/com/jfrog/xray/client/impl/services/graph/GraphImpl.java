package com.jfrog.xray.client.impl.services.graph;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
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
 * Created by romang on 2/27/17.
 */
public class GraphImpl implements Graph {

    private static final ObjectMapper mapper = ObjectMapperHelper.get();
    private static final int MAX_ATTEMPTS = 60;
    private static final int SYNC_SLEEP_INTERVAL = 5000;
    private final XrayClient xray;

    public GraphImpl(XrayClient xray) {
        this.xray = xray;
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false)
                .addFilter("xray-graph-filter", SimpleBeanPropertyFilter.filterOutAllExcept("component_id", "nodes"));
        mapper.setFilterProvider(filters);
    }

    @Override
    public GraphResponse graph(DependencyTree dependencies, String projectKey) throws IOException, InterruptedException {
        if (projectKey == null || projectKey.isEmpty()) {
            return graph(dependencies);
        }
        if (dependencies == null) {
            return new GraphResponseImpl();
        }
        return this.post("?project=" + projectKey, dependencies);
    }

    @Override
    public GraphResponse graph(DependencyTree dependencies) throws IOException, InterruptedException {
        if (dependencies == null) {
            return new GraphResponseImpl();
        }
        return this.post("", dependencies);
    }

    private GraphResponse post(String params, Object body) throws IOException, InterruptedException {
        HttpEntity entity = null;
        // First, request a scan from Xray.
        try (CloseableHttpResponse response = xray.post("scan/graph" + params, body, mapper)) {
            entity = response.getEntity();
            GraphResponse requestResponse = mapper.readValue(response.getEntity().getContent(), GraphResponseImpl.class);
            String scanId = requestResponse.getScanId();
            // If no context was provided (project name), we would like to receive all known vulnerabilities.
            String includeVulnerabilities = !params.isEmpty() ? "" : "&include_vulnerabilities=true";
            // Xray will respond with 201 until the completion of the scan. Once completed, 200 will be returned.
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                try (CloseableHttpResponse res = xray.get("scan/graph/" + scanId + "?include_licenses=true" + includeVulnerabilities)) {
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
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
        // Will get here only in case of timeout, consider to throw exception here...
        return null;
    }
}