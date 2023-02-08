package com.jfrog.xray.client.services.entitlements;

public enum Feature {
    ContextualAnalysis("contextual_analysis");
    private final String name;

    Feature(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}