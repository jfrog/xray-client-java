package com.jfrog.xray.client.services.entitlements;

public enum Feature {
    CONTEXTUAL_ANALYSIS("contextual_analysis"),
    INFRASTRUCTURE_AS_CODE("iac_scanners"),
    SECRETS("secrets_detection");

    private final String name;

    Feature(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}