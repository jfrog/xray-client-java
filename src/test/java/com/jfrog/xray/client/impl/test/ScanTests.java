package com.jfrog.xray.client.impl.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jfrog.xray.client.impl.services.scan.GraphResponseImpl;
import com.jfrog.xray.client.services.scan.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfrog.build.extractor.scan.DependencyTree;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.jfrog.xray.client.impl.services.scan.ScanImpl.createFilteredObjectMapper;
import static org.testng.Assert.*;

/**
 * Created by Tal Arian on 30/09/21.
 */
public class ScanTests extends XrayTestsBase {

    @Test
    public void testGraphScanWithoutContext() throws IOException, InterruptedException {
        GraphResponse response = xray.scan().graph(getDummyTree(), new DummyProgress(), () -> {
        });
        assertFalse(StringUtils.isBlank(response.getScanId()));
    }

    private DependencyTree getDummyTree() {
        DependencyTree tree = new DependencyTree("gav://idea");
        tree.add(new DependencyTree("gav://com.jfrog.ide:ide-plugins-common:1.6.x-SNAPSHOT"));
        tree.add(new DependencyTree("gav://com.jfrog.xray.client:xray-client-java:0.7.x-SNAPSHOT"));
        return tree;
    }

    @Test
    public void testGraphResponse() throws IOException {
        String responseStr = IOUtils.resourceToString("/scan/graph/response.json", StandardCharsets.UTF_8);
        ObjectMapper mapper = createFilteredObjectMapper();
        GraphResponse response = mapper.readValue(responseStr, GraphResponseImpl.class);

        // Check general response details
        assertEquals(response.getPackageType(), "Maven");
        // Check violations exist
        assertEquals(response.getViolations().size(), 21);
        // Check licenses exist
        assertEquals(response.getLicenses().size(), 4);
        // Check vulnerabilities exist
        assertEquals(response.getVulnerabilities().size(), 21);
    }

    @Test
    public void testGraphResponseViolationWithResearch() throws IOException {
        String responseStr = IOUtils.resourceToString("/scan/graph/response-violation-with-research.json", StandardCharsets.UTF_8);
        ObjectMapper mapper = createFilteredObjectMapper();
        GraphResponse response = mapper.readValue(responseStr, GraphResponseImpl.class);

        // Check general response details
        assertEquals(response.getPackageType(), "Generic");

        // Check violation
        assertEquals(response.getViolations().size(), 1);
        Violation violation = response.getViolations().get(0);
        assertEquals(violation.getIssueId(), "XRAY-129823");
        assertEquals(violation.getReferences().size(), 43);
        assertEquals(violation.getIgnoreRuleUrl(), "https://acme.jfrog.io/xray/ir/c970becce");
        assertEquals(violation.getWatchName(), "all-watch");
        assertEquals(violation.getUpdated(), "2022-12-18T11:34:52Z");
        assertEquals(violation.getEdited(), violation.getUpdated());

        // Check extended information
        assertNotNull(violation.getExtendedInformation());
        ExtendedInformation extendedInformation = violation.getExtendedInformation();
        assertEquals(extendedInformation.getJFrogResearchSeverity(), "Low");
        assertEquals(extendedInformation.getShortDescription(), "Improper temporary directory management in Guava can lead to data leakage");
        assertTrue(StringUtils.isNotBlank(extendedInformation.getFullDescription()));
        assertTrue(StringUtils.isNotBlank(extendedInformation.getRemediation()));

        // Check severity reasons
        SeverityReasons[] severityReasons = extendedInformation.getJFrogResearchSeverityReasons();
        assertNotNull(severityReasons);
        assertEquals(3, severityReasons.length);
        for (SeverityReasons severityReason : severityReasons) {
            switch (severityReason.getName()) {
                case "Exploitation of the issue is only possible when the vulnerable component is used in a specific manner. The attacker has to perform per-target research to determine the vulnerable attack vector.":
                case "The issue can only be exploited by an attacker that can execute code on the vulnerable machine (excluding exceedingly rare circumstances)":
                    assertTrue(severityReason.isPositive());
                    break;
                case "A local attacker can simply read files created under `java.io.tmpdir`":
                    assertFalse(severityReason.isPositive());
            }
            assertTrue(StringUtils.isNotBlank(severityReason.getDescription()));
        }
    }

    @Test
    public void testGraphResponseVulnerabilityWithResearch() throws IOException {
        String responseStr = IOUtils.resourceToString("/scan/graph/response-vulnerability-with-research.json", StandardCharsets.UTF_8);
        ObjectMapper mapper = createFilteredObjectMapper();
        GraphResponse response = mapper.readValue(responseStr, GraphResponseImpl.class);

        // Check general response details
        assertEquals(response.getPackageType(), "Generic");

        // Check vulnerability
        assertEquals(response.getVulnerabilities().size(), 1);
        Vulnerability vulnerability = response.getVulnerabilities().get(0);
        assertEquals(vulnerability.getIssueId(), "XRAY-129823");
        assertEquals(vulnerability.getReferences().size(), 43);

        // Check components
        assertEquals(vulnerability.getComponents().size(), 1);
        Component guava = vulnerability.getComponents().get("gav://com.google.guava:guava:19.0");
        assertNotNull(guava);
        assertEquals(guava.getInfectedVersions(), Lists.newArrayList("(,29.0]"));

        // Check extended information
        assertNotNull(vulnerability.getExtendedInformation());
        ExtendedInformation extendedInformation = vulnerability.getExtendedInformation();
        assertEquals(extendedInformation.getJFrogResearchSeverity(), "Low");
        assertEquals(extendedInformation.getShortDescription(), "Improper temporary directory management in Guava can lead to data leakage");
        assertTrue(StringUtils.isNotBlank(extendedInformation.getFullDescription()));
        assertTrue(StringUtils.isNotBlank(extendedInformation.getRemediation()));

        // Check severity reasons
        SeverityReasons[] severityReasons = extendedInformation.getJFrogResearchSeverityReasons();
        assertNotNull(severityReasons);
        assertEquals(3, severityReasons.length);
        for (SeverityReasons severityReason : severityReasons) {
            switch (severityReason.getName()) {
                case "Exploitation of the issue is only possible when the vulnerable component is used in a specific manner. The attacker has to perform per-target research to determine the vulnerable attack vector.":
                case "The issue can only be exploited by an attacker that can execute code on the vulnerable machine (excluding exceedingly rare circumstances)":
                    assertTrue(severityReason.isPositive());
                    break;
                case "A local attacker can simply read files created under `java.io.tmpdir`":
                    assertFalse(severityReason.isPositive());
            }
            assertTrue(StringUtils.isNotBlank(severityReason.getDescription()));
        }
    }

    private static class DummyProgress implements XrayScanProgress {
        @Override
        public void setFraction(double fraction) {
        }
    }
}
