package com.jfrog.xray.client.impl.services.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.summary.VulnerableComponents;

import java.util.List;

/**
 * @author yahavi
 */
public class VulnerableComponentsImpl implements VulnerableComponents {

    @SuppressWarnings("unused")
    @JsonProperty("fixed_versions")
    private List<String> fixedVersions;

    @JsonProperty("fixed_versions")
    @Override
    public List<String> getFixedVersions() {
        return fixedVersions;
    }
}
