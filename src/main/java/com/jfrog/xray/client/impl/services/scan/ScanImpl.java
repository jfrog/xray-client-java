package com.jfrog.xray.client.impl.services.scan;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jfrog.xray.client.impl.XrayClient;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.scan.GraphResponse;
import com.jfrog.xray.client.services.scan.Scan;
import com.jfrog.xray.client.services.scan.XrayScanProgress;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
public class ScanImpl implements Scan {

    private static final ObjectMapper mapper = createFilteredObjectMapper();
    // 30 minutes timeout
    private static final int SYNC_SLEEP_INTERVAL_MILLIS = 10000;
    private static final int MAX_ATTEMPTS = 180;
    private final XrayClient xray;

    public ScanImpl(XrayClient xray) {
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
    public GraphResponse graph(DependencyTree dependencies, XrayScanProgress progress, Runnable checkCanceled, String projectKey, String[] watches, String msi) throws IOException, InterruptedException {
        StringBuilder params = new StringBuilder();

        if (msi != null) {
            params.append("multi_scan_id=").append(msi);
        }
        if (StringUtils.isNotBlank(projectKey)) {
            if (params.length() > 0) {
                params.append("&");
            }
            params.append("project=").append(projectKey);
        }

        if (ArrayUtils.isNotEmpty(watches)) {
            if (params.length() > 0) {
                params.append("&");
            }
            params.append("watch=").append(String.join("&watch=", watches));
        }
        String paramsString = params.toString();

        return !paramsString.isEmpty() ? this.post(paramsString, dependencies, progress, checkCanceled) : graph(dependencies, progress, checkCanceled);

    }

    @Override
    public GraphResponse graph(DependencyTree dependencies, XrayScanProgress progress, Runnable checkCanceled) throws IOException, InterruptedException {
        if (dependencies == null) {
            return new GraphResponseImpl();
        }
        return this.post("", dependencies, progress, checkCanceled);
    }

    private GraphResponse post(String params, Object body, XrayScanProgress progress, Runnable checkCanceled) throws IOException, InterruptedException {
        HttpEntity entity = null;
        // First, request a scan from Xray.
        try (CloseableHttpResponse response = xray.post("scan/graph" + params, body, mapper)) {
            checkCanceled.run();
            entity = response.getEntity();
            GraphResponse requestResponse = mapper.readValue(response.getEntity().getContent(), GraphResponseImpl.class);
            String scanId = requestResponse.getScanId();
            // If no context was provided (project name), we would like to receive all known vulnerabilities.
            String includeVulnerabilities = !params.isEmpty() ? "" : "&include_vulnerabilities=true";
            return getGraphScanResults(scanId, includeVulnerabilities, progress, checkCanceled);
        } finally {
            EntityUtils.consumeQuietly(entity);
            progress.setFraction(1);
        }
    }

    private GraphResponse getGraphScanResults(String scanId, String includeVulnerabilities, XrayScanProgress progress, Runnable checkCanceled) throws IOException, InterruptedException {
        HttpEntity entity = null;
        // Xray will respond with 202 until the completion of the scan. Once completed, 200 will be returned.
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try (CloseableHttpResponse res = xray.get("scan/graph/" + scanId + "?include_licenses=true" + includeVulnerabilities)) {
                checkCanceled.run();
                StatusLine statusLine = res.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    // We got an answer (scan completed), return it.
                    entity = res.getEntity();
                    return mapper.readValue(res.getEntity().getContent(), GraphResponseImpl.class);
                } else if (statusCode == HttpStatus.SC_ACCEPTED) {
                    GraphResponse response = mapper.readValue(res.getEntity().getContent(), GraphResponseImpl.class);
                    progress.setFraction((double) response.getProgressPercentage() / 100);
                }
            } finally {
                EntityUtils.consumeQuietly(entity);
            }
            // Wait between polling attempts.
            Thread.sleep(SYNC_SLEEP_INTERVAL_MILLIS);
        }
        // Will get here only in case of timeout.
        throw new IOException("Xray get graph scan exceeded the timeout.");
    }
}
