package com.jfrog.xray.client.services.summary;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/**
 * @author gonggy
 * @date 2022/6/13
 */
public class IssueSeverityTest {

    @Test
    public void testOf() {
        assertEquals(IssueSeverity.CRITICAL, IssueSeverity.of("critical"));
        assertEquals(IssueSeverity.CRITICAL, IssueSeverity.of("Critical"));
        assertEquals(IssueSeverity.CRITICAL, IssueSeverity.of("CRITICAL"));

        assertEquals(IssueSeverity.HIGH, IssueSeverity.of("high"));
        assertEquals(IssueSeverity.MEDIUM, IssueSeverity.of("medium"));
        assertEquals(IssueSeverity.LOW, IssueSeverity.of("low"));
        assertEquals(IssueSeverity.UNKNOWN, IssueSeverity.of("unknown"));
        assertEquals(IssueSeverity.UNKNOWN, IssueSeverity.of("other"));
        assertEquals(IssueSeverity.UNKNOWN, IssueSeverity.of(null));
    }
}