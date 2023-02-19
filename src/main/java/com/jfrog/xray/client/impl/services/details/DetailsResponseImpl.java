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
    @JsonProperty("build_name")
    private String buildName;
    @JsonProperty("build_number")
    private String buildNumber;
    @JsonProperty("is_scan_completed")
    private boolean isScanCompleted;
    @JsonProperty("components")
    private List<ArtifactImpl> components;
    @JsonProperty("error_details")
    private ErrorImpl error;

    public String getBuildName() {
        return buildName;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public boolean isScanCompleted() {
        return isScanCompleted;
    }

    public List<? extends Artifact> getComponents() {
        return components;
    }

    public Error getError() {
        return error;
    }
}
