package com.jfrog.xray.client.impl.test;

import com.jfrog.xray.client.Xray;
import com.jfrog.xray.client.impl.ComponentsFactory;
import com.jfrog.xray.client.services.summary.Artifact;
import com.jfrog.xray.client.services.summary.Components;
import com.jfrog.xray.client.services.summary.Error;
import com.jfrog.xray.client.services.summary.SummaryResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by romang on 2/27/17.
 */
public class SummaryTests extends XrayTestsBase {

    @Test
    public void testArtifactSummaryNonExistingSha() throws IOException {
        List<String> checksums = new ArrayList<>();
        checksums.add("nonExistingSha");
        SummaryResponse summary = xray.summary().artifact(checksums, null);
        for (Error err : summary.getErrors()) {
            assertEquals(err.getError(), "Artifact doesn't exist or not indexed/cached in Xray");
        }
    }

    @Test
    public void testArtifactSummaryComponent() throws IOException {
        testArtifactSummaryComponent(xray);
    }

    @Test
    public void testArtifactSummaryComponentProxy() throws IOException {
        testArtifactSummaryComponent(xrayProxies);
        Expectation[] expectations = mockServer.retrieveRecordedExpectations(null);
        assertEquals(1, ArrayUtils.getLength(expectations));

        // Assert that request passed through proxy
        String requestBody = ((HttpRequest) expectations[0].getHttpRequest()).getBody().getValue().toString();
        assertTrue(StringUtils.contains(requestBody, "spring-security-oauth2"));

        // Assert that response passed through proxy
        String responseBody = expectations[0].getHttpResponse().getBody().getValue().toString();
        assertTrue(StringUtils.contains(responseBody, "spring-security-oauth2"));
    }

    private void testArtifactSummaryComponent(Xray xray) throws IOException {
        Components components = ComponentsFactory.create();
        components.addComponent("gav://org.springframework.security.oauth:spring-security-oauth2:2.2.3.RELEASE", "");
        SummaryResponse summary = xray.summary().component(components);

        assertNull(summary.getErrors());
        assertNotNull(summary.getArtifacts());
        assertNotNull(summary.getArtifacts());
        assertEquals(summary.getArtifacts().size(), 1, "Expecting one Artifact only.");

        for (Artifact artifact : summary.getArtifacts()) {
            assertNotNull(artifact.getGeneral());
            assertNotNull(artifact.getLicenses());
            assertNotNull(artifact.getIssues());
            assertEquals(artifact.getIssues().size(), 1);
            assertNotNull(artifact.getIssues().get(0).getVulnerableComponents());
            assertEquals(artifact.getIssues().get(0).getVulnerableComponents().size(), 2);
            assertEquals(artifact.getIssues().get(0).getVulnerableComponents().get(0).getFixedVersions().size(), 4);
        }
    }

    @Test
    public void testArtifactSummaryNonExistingPath() throws IOException {
        List<String> paths = new ArrayList<>();
        paths.add("non/existing/path");
        SummaryResponse summary = xray.summary().artifact(null, paths);
        for (Error err : summary.getErrors()) {
            assertEquals(err.getError(), "Artifact doesn't exist or not indexed/cached in Xray");
        }
    }

    @Test
    public void testArtifactSummary() throws IOException {
        SummaryResponse summary = xray.summary().artifact(null, null);
        assertNull(summary.getArtifacts());
        assertNull(summary.getErrors());
    }
}
