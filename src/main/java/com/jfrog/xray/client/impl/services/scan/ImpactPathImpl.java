package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.graph.ImpactPath;

public class ImpactPathImpl implements ImpactPath {

    private String id;
    private String fullPath;

    @Override
    @JsonProperty("component_id")
    public String getComponentId() {
        return id;
    }

    @JsonProperty("component_id")
    public void setComponentId(String id) {
        this.id = id;
    }

    @Override
    @JsonProperty("full_path")
    public String getFullPath() {
        return fullPath;
    }

    @JsonProperty("full_path")
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
