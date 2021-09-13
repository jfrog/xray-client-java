package com.jfrog.xray.client.impl.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.graph.Component;

import java.util.List;

public class ComponentImpl implements Component {
    private List<String> fixedVersions = null;
    private List<String> impactPaths = null;
    @Override
    @JsonProperty("fixed_versions")
    public List<String> getFixedVersions() { return fixedVersions; }

    @Override
    @JsonProperty("impact_paths")
    public List<String> getImpactPaths() { return impactPaths; }
}
