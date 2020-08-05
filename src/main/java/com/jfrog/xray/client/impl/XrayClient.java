package com.jfrog.xray.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfrog.xray.client.Xray;
import com.jfrog.xray.client.impl.services.summary.SummaryImpl;
import com.jfrog.xray.client.impl.services.system.SystemImpl;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.impl.util.URIUtil;
import com.jfrog.xray.client.services.summary.Summary;
import com.jfrog.xray.client.services.system.System;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jfrog.build.api.util.Log;
import org.jfrog.build.client.PreemptiveHttpClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * @author Roman Gurevitch
 */
public class XrayClient extends PreemptiveHttpClient implements Xray {
    private static final ObjectMapper mapper = ObjectMapperHelper.get();
    private static final String API_BASE = "/api/v1/";

    private final String baseApiUrl;
    private final Log log;

    public XrayClient(PoolingHttpClientConnectionManager connectionManager, BasicCredentialsProvider credentialsProvider, String accessToken, AuthCache authCache, HttpClientBuilder clientBuilder, int connectionRetries, Log log, String url) {
        super(connectionManager, credentialsProvider, accessToken, authCache, clientBuilder, connectionRetries, log);
        this.baseApiUrl = URIUtil.concatUrl(url, API_BASE);
        this.log = log;
    }

    public static void addContentTypeJsonHeader(Map<String, String> headers) {
        headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
    }

    @SuppressWarnings("unused")
    public static void addContentTypeBinaryHeader(Map<String, String> headers) {
        headers.put(HttpHeaders.CONTENT_TYPE, ContentType.DEFAULT_BINARY.getMimeType());
    }

    private static boolean statusNotOk(int statusCode) {
        return statusCode != HttpStatus.SC_OK
                && statusCode != HttpStatus.SC_CREATED
                && statusCode != HttpStatus.SC_ACCEPTED;
    }

    @Override
    public System system() {
        return new SystemImpl(this);
    }

    @Override
    public Summary summary() {
        return new SummaryImpl(this);
    }

    private void setHeaders(HttpUriRequest request, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                request.setHeader(header.getKey(), header.getValue());
            }
        }
    }

    public HttpResponse get(String uri, Map<String, String> headers) throws IOException {
        HttpGet getRequest = new HttpGet(createUrl(uri));
        log.debug("GET " + getRequest.getURI());
        return setHeadersAndExecute(getRequest, headers);
    }

    @SuppressWarnings("unused")
    public HttpResponse head(String uri, Map<String, String> headers) throws IOException {
        HttpHead headRequest = new HttpHead(createUrl(uri));
        log.debug("HEAD " + headRequest.getURI());
        return setHeadersAndExecute(headRequest, headers);
    }

    public HttpResponse post(String uri, Map<String, String> headers, Object payload) throws IOException {
        HttpPost postRequest = new HttpPost(createUrl(uri));
        byte[] body = mapper.writeValueAsBytes(payload);
        log.debug("POST " + postRequest.getURI() + "\n" + new String(body, StandardCharsets.UTF_8));
        try (ByteArrayInputStream content = new ByteArrayInputStream(body)) {
            HttpEntity requestEntity = new InputStreamEntity(content);
            postRequest.setEntity(requestEntity);
            return setHeadersAndExecute(postRequest, headers);
        }
    }

    private String createUrl(String queryPath) {
        return URIUtil.concatUrl(baseApiUrl, queryPath);
    }

    private HttpResponse setHeadersAndExecute(HttpUriRequest request, Map<String, String> headers) throws IOException {
        setHeaders(request, headers);
        HttpResponse response = execute(request);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusNotOk(statusCode)) {
            String body = null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    body = readStream(entity.getContent());
                } catch (IOException e) {
                    // Ignore
                } finally {
                    EntityUtils.consume(entity);
                }
            }
            String message = String.format("Received %d %s response from Xray", statusCode, statusLine);
            if (StringUtils.isNotBlank(body)) {
                message += ". " + body;
            }
            throw new HttpResponseException(statusCode, message);
        }
        log.debug("Received status " + statusCode + " for " + request.getMethod() + " " + request.getURI());
        return response;
    }

    private static String readStream(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        try (StringWriter writer = new StringWriter()) {
            IOUtils.copy(stream, writer, "UTF-8");
            return writer.toString();
        }
    }
}
