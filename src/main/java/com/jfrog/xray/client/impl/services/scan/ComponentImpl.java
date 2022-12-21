package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.scan.Component;

import java.util.List;

public class ComponentImpl implements Component {
    private List<String> fixedVersions;
    private List<String> infectedVersions;
    private List<List<ImpactPathImpl>> impactPaths;

    @Override
    @JsonProperty("fixed_versions")
    public List<String> getFixedVersions() {
        return fixedVersions;
    }

    @Override
    @JsonProperty("infected_versions")
    public List<String> getInfectedVersions() {
        return infectedVersions;
    }

    @JsonProperty("fixed_versions")
    public void setFixedVersions(List<String> fixedVersions) {
        this.fixedVersions = fixedVersions;
    }

    @JsonProperty("infected_versions")
    public void setInfectedVersions(List<String> infectedVersions) {
        this.infectedVersions = infectedVersions;
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
