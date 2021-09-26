package com.jfrog.xray.client.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.impl.services.graph.ImpactPathImpl;

import java.util.List;

public interface Component {
    @JsonProperty("fixed_versions")
    List<String> getFixedVersions();

    @JsonProperty("impact_paths")
    List<List<ImpactPathImpl>>  getImpactPaths();
}
