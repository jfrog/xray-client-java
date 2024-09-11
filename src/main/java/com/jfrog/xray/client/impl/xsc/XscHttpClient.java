package com.jfrog.xray.client.impl.xsc;

import com.jfrog.xray.client.impl.XrayClient;
import org.apache.http.client.AuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jfrog.build.api.util.Log;


public class XscHttpClient extends XrayClient {
    private static final String XSC_BASE_URL = "/xsc/api/v1";


    public XscHttpClient(PoolingHttpClientConnectionManager connectionManager, BasicCredentialsProvider credentialsProvider, String accessToken, AuthCache authCache, HttpClientBuilder clientBuilder, int connectionRetries, Log log, String url) {
        super(connectionManager, credentialsProvider, accessToken, authCache, clientBuilder, connectionRetries, log, url + XSC_BASE_URL);
    }

    public String extractValueFromResponse(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) {
            return null;
        }
        startIndex += searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        if (endIndex == -1) {
            return null;
        }
        return json.substring(startIndex, endIndex);
    }
}
