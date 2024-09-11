package com.jfrog.xray.client.impl.test;

import com.jfrog.xray.client.Xray;
import com.jfrog.xray.client.impl.XrayClientBuilder;
import com.jfrog.xray.client.impl.util.JFrogInactiveEnvironmentException;
import com.jfrog.xray.client.services.system.Version;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpHeaders;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.apache.http.HttpHeaders.LOCATION;
import static org.apache.http.client.protocol.HttpClientContext.REDIRECT_LOCATIONS;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
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

    @Test
    public void testJFrogInactiveEnv() throws IOException {
        mockServer.when(request().withPath("/xray/api/v1/system/version")).respond(response().withBody("{}").withStatusCode(302).withHeader(LOCATION, "http://localhost:8888/reactivate-server/eco-server"));
        mockServer.when(request().withPath("/reactivate-server/eco-server")).respond(response().withBody("{}").withStatusCode(200));
        try (Xray xrayMock = new XrayClientBuilder(null).setUrl("http://localhost:8888/xray/").build()) {
            assertThrows(JFrogInactiveEnvironmentException.class, () -> xrayMock.system().version());
        }
    }

    @Test
    public void testJFrogInactiveEnvFalsePositives() throws IOException {
        // The redirection response doesn't contain "reactivate-server" so it should not be detected as JFrogInactiveEnv
        mockServer.when(request().withPath("/xray/api/v1/system/version")).respond(response().withBody("{}").withStatusCode(302).withHeader(LOCATION, "http://localhost:8888/eco-server"));
        mockServer.when(request().withPath("/eco-server")).respond(response().withBody("{\"xray_version\":\"3.66.4\",\"xray_revision\":\"4cae8b1\"}").withStatusCode(200));
        try (Xray xrayMock = new XrayClientBuilder(null).setUrl("http://localhost:8888/xray/").build()) {
            assertEquals(xrayMock.system().version().getVersion(), "3.66.4");
        }
    }

    private void testVersion(Xray xray) throws IOException {
        Version version = xray.system().version();
        assertNotNull(version.getVersion());
        assertNotNull(version.getRevision());
    }
}
