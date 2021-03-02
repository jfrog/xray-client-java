package com.jfrog.xray.client.impl.services.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfrog.xray.client.impl.XrayClient;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.system.System;
import com.jfrog.xray.client.services.system.Version;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by romang on 2/2/17.
 */
public class SystemImpl implements System {

    private final String BASE_API = "v1/system/";
    private static final ObjectMapper mapper = ObjectMapperHelper.get();
    private final XrayClient xray;

    public SystemImpl(XrayClient xray) {
        this.xray = xray;
    }

    @Override
    public boolean ping() {
        HttpEntity entity = null;
        try (CloseableHttpResponse response = xray.get(BASE_API + "ping")) {
            entity = response.getEntity();
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (Exception e) {
            return false;
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    @Override
    public Version version() throws IOException {
        HttpEntity entity = null;
        try (CloseableHttpResponse response = xray.get(BASE_API + "version")) {
            entity = response.getEntity();
            return mapper.readValue(response.getEntity().getContent(), VersionImpl.class);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }
}
