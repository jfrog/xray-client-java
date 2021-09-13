package com.jfrog.xray.client.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public interface Component {
    @JsonProperty("fixed_versions")
    List<String> getFixedVersions();

    @JsonProperty("impact_paths")
    List<String> getImpactPaths();
}
