package com.jfrog.xray.client.services.entitlements;

public enum Feature {
    ContextualAnalysis("contextual_analysis"),
    InfrastructureAsCode("iac_scanners"),
    SecretsScanner("secrets_detection");

    private final String name;

    Feature(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}