package com.jfrog.xray.client.impl.services.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.impl.services.summary.ArtifactImpl;
import com.jfrog.xray.client.services.details.DetailsResponse;
import com.jfrog.xray.client.services.details.Error;
import com.jfrog.xray.client.services.summary.Artifact;

import java.util.List;

/**
 * @author yahavi
 **/
@SuppressWarnings("unused")
public class DetailsResponseImpl implements DetailsResponse {
    private String buildName;
    private String buildNumber;
    private boolean scanCompleted;
    private List<ArtifactImpl> components;
    private ErrorImpl error;

    @JsonProperty("build_name")
    public String getBuildName() {
        return buildName;
    }

    @JsonProperty("build_number")
    public String getBuildNumber() {
        return buildNumber;
    }

    @JsonProperty("is_scan_completed")
    public boolean getScanCompleted() {
        return scanCompleted;
    }

    @JsonProperty("components")
    public List<? extends Artifact> getComponents() {
        return components;
    }

    @JsonProperty("error_details")
    public Error getError() {
        return error;
    }
}
