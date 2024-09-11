package com.jfrog.xray.client.impl.xsc;

import com.jfrog.xray.client.impl.xsc.types.EndScanRequest;
import com.jfrog.xray.client.impl.xsc.types.ScanRequest;

public class XscEventClient {
    private final String EVENT_ENDPOINT = "/event";
    private final XscHttpClient httpClient;

    public XscEventClient(XscHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String startScan(ScanRequest scanRequest) {
        try {
            String response = httpClient.post(EVENT_ENDPOINT, scanRequest).toString();
            return httpClient.extractValueFromResponse(response, "multi_scan_id");
        } catch (Exception e) {
            return "";
        }
    }

    public void endScan(EndScanRequest scanRequest) {
        try {
            httpClient.put(EVENT_ENDPOINT, scanRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}