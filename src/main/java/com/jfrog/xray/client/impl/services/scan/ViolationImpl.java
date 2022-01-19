package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.impl.services.common.CveImpl;
import com.jfrog.xray.client.services.common.Cve;
import com.jfrog.xray.client.services.scan.Component;
import com.jfrog.xray.client.services.scan.Violation;

import java.util.List;
import java.util.Map;

public class ViolationImpl implements Violation {
    private String summary;
    private String severity;
    private String issueId;
    private String licenseKey;
    private String licenseName;
    private Map<String, ComponentImpl> components;
    private List<CveImpl> cves;
    private List<String> references;

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
    @JsonProperty("license_key")
    public String getLicenseKey() {
        return licenseKey;
    }

    @Override
    @JsonProperty("license_name")
    public String getLicenseName() {
        return licenseName;
    }
}
