package com.jfrog.xray.client.impl;

import com.jfrog.xray.client.Xray;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jfrog.client.http.HttpBuilderBase;
import org.jfrog.client.http.auth.PreemptiveAuthInterceptor;
import org.jfrog.client.http.model.ProxyConfig;
import org.jfrog.client.util.KeyStoreProvider;

/**
 * Created by romang on 2/2/17.
 */
public class XrayClient {

    private static final int CONNECTION_TIMEOUT_MILLISECONDS = 300 * 1000;
    private static final String DEFAULT_USER_AGENT = "jfrog-xray-client/" + XrayClient.class.getPackage().getImplementationVersion();

    public static Xray create(CloseableHttpClient preConfiguredClient, String url) {
        return new XrayImpl(preConfiguredClient, url);
    }

    /**
     * Username, API key, and custom url
     */
    public static Xray create(String url, String username, String password, String userAgent,
                              boolean noHostVerification, KeyStoreProvider keyStoreProvider, ProxyConfig proxyConfig) {
        HttpBuilderBase configurator = new HttpBuilderBase() {
        };
        configurator.hostFromUrl(url)
                .authentication(username, password, true)
                .connectionTimeout(CONNECTION_TIMEOUT_MILLISECONDS)
                .socketTimeout(CONNECTION_TIMEOUT_MILLISECONDS)
                .userAgent(userAgent)
                .proxy(proxyConfig)
                .noHostVerification(noHostVerification)
                .keyStoreProvider(keyStoreProvider)
                .trustSelfSignCert(true)
                .addRequestInterceptor(new PreemptiveAuthInterceptor());

        return new XrayImpl(configurator.build(), url);
    }

    public static Xray create(String url, String username, String password, boolean noHostVerification, KeyStoreProvider keyStoreProvider, ProxyConfig proxyConfig) {
        return create(url, username, password, DEFAULT_USER_AGENT, noHostVerification, keyStoreProvider, proxyConfig);
    }
}