package com.jfrog.xray.client.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jfrog.build.api.util.NullLog;
import org.jfrog.build.client.PreemptiveHttpClientBuilder;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * @author yahavi
 */
public class XrayClientBuilder extends PreemptiveHttpClientBuilder {

    private static final String DEFAULT_USER_AGENT = "jfrog-xray-client/" + XrayClient.class.getPackage().getImplementationVersion();
    // 1 minute timeout
    private static final int CONNECTION_TIMEOUT_MILLISECONDS = (int) MINUTES.toMillis(1);

    private String url = StringUtils.EMPTY;

    public XrayClientBuilder(String userAgent) {
        String userAgentHeader = DEFAULT_USER_AGENT;
        if (userAgent != null && !userAgent.isEmpty()) {
            userAgentHeader = userAgent;
        }

        setTimeout(CONNECTION_TIMEOUT_MILLISECONDS);
        setUserAgent(userAgentHeader);
        setLog(new NullLog());
    }

    public XrayClientBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public XrayClient build() {
        buildConnectionManager();
        HttpClientBuilder httpClientBuilder = createHttpClientBuilder();
        createCredentialsAndAuthCache();
        return new XrayClient(connectionManager, credentialsProvider, accessToken, authCache, httpClientBuilder, connectionRetries, log, url);
    }
}
