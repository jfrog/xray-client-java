package com.jfrog.xray.client.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.common.Cve;

import java.util.List;
import java.util.Map;

public interface Violation {

    @JsonProperty("summary")
    String getSummary();

    @JsonProperty("severity")
    String getSeverity();

    @JsonProperty("type")
    String getViolationType();

    @JsonProperty("components")
    Map<String, ? extends Component> getComponents();

    @JsonProperty("watch_name")
    String getWatchName();

    @JsonProperty("issue_id")
    String getIssueId();

    @JsonProperty("cves")
    List<? extends Cve> getCves();

    @JsonProperty("references")
    List<String> getReferences();

    @JsonProperty("fail_build")
    boolean getFailBuild();

    @JsonProperty("license_key")
    String getLicenseKey();

    @JsonProperty("license_name")
    String getLicenseName();

    @JsonProperty("ignore_url")
    String getIgnoreUrl();
}