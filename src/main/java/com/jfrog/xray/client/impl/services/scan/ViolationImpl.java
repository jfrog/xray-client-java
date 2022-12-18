package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.impl.services.common.CveImpl;
import com.jfrog.xray.client.services.common.Cve;
import com.jfrog.xray.client.services.scan.Component;
import com.jfrog.xray.client.services.scan.ExtendedInformation;
import com.jfrog.xray.client.services.scan.Violation;

import java.util.List;
import java.util.Map;

public class ViolationImpl implements Violation {
    private String updated;
    private String summary;
    private String severity;
    private String issueId;
    private String licenseKey;
    private String licenseName;
    private Map<String, ComponentImpl> components;
    private List<CveImpl> cves;
    private List<String> references;
    private String ignoreRuleUrl;
    private String watchName;
    private ExtendedInformationImpl extendedInformation;

    @Override
    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @Override
    @JsonProperty("cves")
    public List<? extends Cve> getCves() {
        return cves;
    }

    @Override
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @Override
    @JsonProperty("severity")
    public String getSeverity() {
        return severity;
    }

    @Override
    @JsonProperty("components")
    public Map<String, ? extends Component> getComponents() {
        return components;
    }

    @Override
    @JsonProperty("issue_id")
    public String getIssueId() {
        return issueId;
    }

    @Override
    @JsonProperty("references")
    public List<String> getReferences() {
        return references;
    }

    @Override
    @JsonProperty("ignore_url")
    public String getIgnoreRuleUrl() {
        return ignoreRuleUrl;
    }

    @Override
    @JsonProperty("license_key")
    public String getLicenseKey() {
        return licenseKey;
    }

    @Override
    @JsonProperty("license_name")
    public String getLicenseName() {
        return licenseName;
    }

    @Override
    @JsonProperty("watch_name")
    public String getWatchName() {
        return watchName;
    }

    @Override
    @JsonProperty("extended_information")
    public ExtendedInformation getExtendedInformation() {
        return extendedInformation;
    }
}
