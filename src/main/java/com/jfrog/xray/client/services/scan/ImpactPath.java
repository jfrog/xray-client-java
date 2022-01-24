package com.jfrog.xray.client.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ImpactPath {
    @JsonProperty("component_id")
    String getComponentId();

    @JsonProperty("full_path")
    String getFullPath();
}
