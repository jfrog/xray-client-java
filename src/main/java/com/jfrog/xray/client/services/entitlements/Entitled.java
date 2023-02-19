package com.jfrog.xray.client.services.entitlements;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Entitled {
    @JsonProperty("entitled")
    boolean isEntitled();

    @JsonProperty("feature_id")
    String getFeatureID();
}
