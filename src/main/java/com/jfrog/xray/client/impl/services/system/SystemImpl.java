package com.jfrog.xray.client.impl.services.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfrog.xray.client.impl.XrayClient;
import com.jfrog.xray.client.impl.util.HttpUtils;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.system.System;
import com.jfrog.xray.client.services.system.Version;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;

/**
 * Created by romang on 2/2/17.
 */
public class SystemImpl implements System {

    private static final ObjectMapper mapper = ObjectMapperHelper.get();
    private final XrayClient xray;

    public SystemImpl(XrayClient xray) {
        this.xray = xray;
    }

    @Override
    public boolean ping() {
        HttpResponse response = null;
        try {
            response = xray.get("system/ping", null);
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (Exception e) {
            return false;
        } finally {
            HttpUtils.consumeResponse(response);
        }
    }

    @Override
    public Version version() throws IOException {
        HttpResponse response = null;
        try {
            response = xray.get("system/version", null);
            return mapper.readValue(response.getEntity().getContent(), VersionImpl.class);
        } finally {
            HttpUtils.consumeResponse(response);
        }
    }
}
