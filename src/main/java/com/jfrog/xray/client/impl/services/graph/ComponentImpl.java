package com.jfrog.xray.client.impl.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.graph.Component;

import java.util.List;

public class ComponentImpl implements Component {
    private List<String> fixedVersions = null;
    private List<List<ImpactPathImpl>> impactPaths = null;

    @Override
    @JsonProperty("fixed_versions")
    public List<String> getFixedVersions() {
        return fixedVersions;
    }

    @JsonProperty("fixed_versions")
    public void setFixedVersions(List<String> fixedVersions) {
        this.fixedVersions = fixedVersions;
    }

    @Override
    @JsonProperty("impact_paths")
    public List<List<ImpactPathImpl>> getImpactPaths() {
        return impactPaths;
    }

    @JsonProperty("impact_paths")
    public void setImpactPaths(List<List<ImpactPathImpl>> impactPaths) {
        this.impactPaths = impactPaths;
    }
}
