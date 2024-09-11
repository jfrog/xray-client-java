package com.jfrog.xray.client.impl.xsc;

import org.apache.http.client.AuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jfrog.build.api.util.Log;

public class XscClient {

    private XscHttpClient httpClient;
    private XcsSystemClient xscSystemClient;
    private XscEventClient xscEventClient;

    public XscClient(PoolingHttpClientConnectionManager connectionManager, BasicCredentialsProvider credentialsProvider, String accessToken, AuthCache authCache, HttpClientBuilder clientBuilder, int connectionRetries, Log log, String url) {
        this.httpClient = new XscHttpClient(connectionManager, credentialsProvider, accessToken, authCache, clientBuilder, connectionRetries, log, url);
        this.xscSystemClient = new XcsSystemClient(this.httpClient);
        this.xscEventClient = new XscEventClient(this.httpClient);
    }

    public XscEventClient scan() {
        return this.xscEventClient;
    }

    public XcsSystemClient system() {
        return this.xscSystemClient;
    }

}
