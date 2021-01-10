package com.jfrog.xray.client.impl.test;

import com.jfrog.xray.client.Xray;
import com.jfrog.xray.client.services.system.Version;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

/**
 * Created by romang on 2/2/17.
 */
public class SystemTests extends XrayTestsBase {

    @Test
    public void testPing() {
        assertTrue(xray.system().ping());
    }

    @Test
    public void testPingProxy() {
        assertTrue(xrayProxies.system().ping());

        // Assert that request passed through proxy
        Expectation[] expectations = mockServer.retrieveRecordedExpectations(null);
        assertEquals(1, ArrayUtils.getLength(expectations));
        String requestPath = ((HttpRequest) expectations[0].getHttpRequest()).getPath().toString();
        assertTrue(StringUtils.contains(requestPath, "system/ping"));

        // Assert that response passed through proxy
        assertEquals(200, expectations[0].getHttpResponse().getStatusCode().intValue());
    }

    @Test
    public void testVersion() throws IOException {
        testVersion(xray);
    }

    @Test
    public void testVersionProxy() throws IOException {
        testVersion(xrayProxies);
        Expectation[] expectations = mockServer.retrieveRecordedExpectations(null);
        assertEquals(1, ArrayUtils.getLength(expectations));

        // Assert that request passed through proxy
        String requestPath = ((HttpRequest) expectations[0].getHttpRequest()).getPath().toString();
        assertTrue(StringUtils.contains(requestPath, "system/version"));

        // Assert that response passed through proxy
        assertEquals(200, expectations[0].getHttpResponse().getStatusCode().intValue());
    }

    private void testVersion(Xray xray) throws IOException {
        Version version = xray.system().version();
        assertNotNull(version.getVersion());
        assertNotNull(version.getRevision());
    }
}
