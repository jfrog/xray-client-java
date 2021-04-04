package com.jfrog.xray.client.impl.services.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.summary.Artifact;
import com.jfrog.xray.client.services.summary.Error;
import com.jfrog.xray.client.services.summary.SummaryResponse;

import java.util.List;

/**
 * Created by romang on 2/27/17.
 */
public class SummaryResponseImpl implements SummaryResponse {

    private String buildName;
    private String buildNumber;
    private boolean scanCompleted;
    private List<ArtifactImpl> artifacts = null;
    private List<ErrorImpl> errors = null;

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

    @JsonProperty("artifacts")
    public List<? extends Artifact> getArtifacts() {
        return artifacts;
    }

    @JsonProperty("errors")
    public List<? extends Error> getErrors() {
        return errors;
    }
}
