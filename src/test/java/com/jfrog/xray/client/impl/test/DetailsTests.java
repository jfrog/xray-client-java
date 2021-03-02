package com.jfrog.xray.client.impl.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfrog.xray.client.impl.services.details.DetailsResponseImpl;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.details.DetailsResponse;
import com.jfrog.xray.client.services.details.Error;
import com.jfrog.xray.client.services.summary.Artifact;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.testng.Assert.*;

/**
 * @author yahavi
 **/
public class DetailsTests extends XrayTestsBase {
    private static final ObjectMapper mapper = ObjectMapperHelper.get();

    @Test
    public void testDetailsBuild() throws IOException {
        String responseStr = IOUtils.resourceToString("/details/build/response.json", StandardCharsets.UTF_8);
        DetailsResponse response = mapper.readValue(responseStr, DetailsResponseImpl.class);

        // Check general build details
        assertEquals(response.getBuildName(), "maven-build");
        assertEquals(response.getBuildNumber(), "1");
        assertTrue(response.getScanCompleted());
        assertNull(response.getError());

        // Check build dependency
        Artifact commonsIo = response.getComponents().stream()
                .filter(component -> "commons-io:commons-io:1.4".equals(component.getGeneral().getComponentId()))
                .findAny().orElse(null);
        assertNotNull(commonsIo);

        // Check issue exist
        assertEquals(1, commonsIo.getIssues().size());
        assertEquals("Apache Commons IO io/FileUtils.java Unsafe Directory Creation Weakness", commonsIo.getIssues().get(0).getSummary());

        // Check license exist
        assertEquals(1, commonsIo.getLicenses().size());
        assertEquals("Apache-2.0", commonsIo.getLicenses().get(0).getName());
    }

    @Test
    public void testDetailsBuildNotExist() throws IOException {
        DetailsResponse response = xray.details().build("buildNotExist", "18");

        // Check general build details
        assertEquals(response.getBuildName(), "buildNotExist");
        assertEquals(response.getBuildNumber(), "18");
        assertFalse(response.getScanCompleted());
        assertNull(response.getComponents());

        // Check error
        assertNotNull(response.getError());
        Error error = response.getError();
        assertTrue(StringUtils.isNotBlank(error.getErrorCode()));
        assertTrue(StringUtils.isNotBlank(error.getMessage()));
    }
}
