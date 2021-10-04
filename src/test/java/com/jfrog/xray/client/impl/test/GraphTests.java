package com.jfrog.xray.client.impl.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jfrog.xray.client.impl.services.graph.GraphResponseImpl;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.graph.GraphResponse;
import org.apache.commons.io.IOUtils;
import org.jfrog.build.extractor.scan.DependencyTree;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * Created by Tal Arian on 30/09/21.
 */
public class GraphTests extends XrayTestsBase {
    
    @Test
    public void testGraphScanWithoutContext() throws IOException, InterruptedException {
        GraphResponse response = xray.graph().graph(getDummyTree(), () -> {
        });
        assertFalse(response.getScanId() == null || response.getScanId().isEmpty());

    }

    private DependencyTree getDummyTree() {
        DependencyTree tree = new DependencyTree("gav://idea");
        Vector<DependencyTree> children = tree.getChildren();
        children.add(new DependencyTree("gav://com.jfrog.ide:ide-plugins-common:1.6.x-SNAPSHOT"));
        children.add(new DependencyTree("gav://com.jfrog.xray.client:xray-client-java:0.7.x-SNAPSHOT"));
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

    private ObjectMapper createFilteredObjectMapper() {
        ObjectMapper mapper = ObjectMapperHelper.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false)
                .addFilter("xray-graph-filter", SimpleBeanPropertyFilter.filterOutAllExcept("component_id", "nodes"));
        mapper.setFilterProvider(filters);
        return mapper;
    }
}
