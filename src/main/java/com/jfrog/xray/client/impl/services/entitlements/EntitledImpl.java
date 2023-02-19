package com.jfrog.xray.client.impl.services.entitlements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.entitlements.Entitled;

public class EntitledImpl implements Entitled {

    @JsonProperty("entitled")
    private boolean isEntitled;
    @JsonProperty("feature_id")
    private String featureID;

    @Override
    public boolean isEntitled() {
        return isEntitled;
    }

    @Override
    public String getFeatureID() {
        return featureID;
    }
}
