package com.jfrog.xray.client.impl.util;

import org.apache.http.client.HttpResponseException;

public class JFrogInactiveEnvironmentException extends HttpResponseException {
    private final String redirectUrl;

    public JFrogInactiveEnvironmentException(int statusCode, String reasonPhrase, String redirectUrl) {
        super(statusCode, reasonPhrase);
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
