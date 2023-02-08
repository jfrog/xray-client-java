package com.jfrog.xray.client.impl.test;

import com.jfrog.xray.client.services.entitlements.Feature;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class EntitlementsTests extends XrayTestsBase {

    @Test
    public void testIsEntitled() {
        Boolean isEntitled = xray.entitlements().isEntitled(Feature.ContextualAnalysis);
        assertNotNull(isEntitled);
    }
}
