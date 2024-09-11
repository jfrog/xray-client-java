package com.jfrog.xray.client.impl.xsc;

import java.io.IOException;

public class XcsSystemClient {
    private final String SYSTEM_ENDPOINT = "/system/version";
    private final XscHttpClient httpClient;

    public XcsSystemClient(XscHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getXscVersion() {
        try {
            String response = this.httpClient.get(SYSTEM_ENDPOINT).toString();
            return this.httpClient.extractValueFromResponse(response, "xsc_version");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

}
