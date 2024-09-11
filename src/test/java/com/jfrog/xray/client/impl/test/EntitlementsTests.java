package com.jfrog.xray.client.impl.test;

import com.jfrog.xray.client.Xray;
import com.jfrog.xray.client.impl.XrayClientBuilder;
import com.jfrog.xray.client.services.entitlements.Feature;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.testng.Assert.*;

public class EntitlementsTests extends XrayTestsBase {
    @Test
    public void testIsEntitledParse() throws IOException {
        try (Xray xrayMock = new XrayClientBuilder(null).setUrl("http://localhost:8888/xray/").build()) {
            mockServer.when(request().withPath("/xray/api/v1/entitlements/feature/" + Feature.CONTEXTUAL_ANALYSIS)).respond(response().withBody("{\"entitled\":true,\"feature_id\":\"" + Feature.CONTEXTUAL_ANALYSIS + "\"}").withStatusCode(200));
            assertTrue(xrayMock.entitlements().isEntitled(Feature.CONTEXTUAL_ANALYSIS));
        }
    }

    @Test
    public void testIsEntitled() throws IOException {
        Boolean isEntitled = xray.entitlements().isEntitled(Feature.CONTEXTUAL_ANALYSIS);
        assertNotNull(isEntitled);
    }
}
