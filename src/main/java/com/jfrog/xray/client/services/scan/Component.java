package com.jfrog.xray.client.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.impl.services.scan.ImpactPathImpl;

import java.util.List;

public interface Component {
    @JsonProperty("fixed_versions")
    List<String> getFixedVersions();

    @JsonProperty("impact_paths")
    List<List<ImpactPathImpl>>  getImpactPaths();
}
