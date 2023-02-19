package com.jfrog.xray.client.impl.services.entitlements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfrog.xray.client.impl.XrayClient;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.entitlements.Entitled;
import com.jfrog.xray.client.services.entitlements.Entitlements;
import com.jfrog.xray.client.services.entitlements.Feature;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class EntitlementsImpl implements Entitlements {
    private final XrayClient xray;
    private static final ObjectMapper mapper = ObjectMapperHelper.get();

    public EntitlementsImpl(XrayClient xray) {
        this.xray = xray;
    }

    @Override
    public boolean isEntitled(Feature feature) throws IOException {
        HttpEntity entity = null;
        try (CloseableHttpResponse response = xray.get(String.format("entitlements/feature/%s", feature.toString()))) {
            entity = response.getEntity();
            Entitled requestResponse = mapper.readValue(response.getEntity().getContent(), EntitledImpl.class);
            return requestResponse.isEntitled();
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }
}
