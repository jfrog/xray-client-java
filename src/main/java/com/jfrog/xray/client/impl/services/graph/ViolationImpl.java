package com.jfrog.xray.client.impl.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.graph.Component;
import com.jfrog.xray.client.services.graph.Cve;
import com.jfrog.xray.client.services.graph.Violation;

import java.util.List;
import java.util.Map;

public class ViolationImpl implements Violation {
    private String summary;
    private String severity;
    private String violationType;
    private String watchName;
    private String issueId;
    private String licenseKey;
    private String licenseName;
    private String ignoreUrl;
    private boolean failBuild;
    private Map<String, ComponentImpl> components;
    private List<CveImpl> cves;
    private List<String> references;

    @Override
    @JsonProperty("cves")
    public List<? extends Cve> getCves() { return cves; }

    @Override
    @JsonProperty("summary")
    public String getSummary() { return summary; }

    @Override
    @JsonProperty("severity")
    public String getSeverity() { return severity; }

    @Override
    @JsonProperty("components")
    public Map<String, ? extends Component> getComponents() { return components; }

    @Override
    @JsonProperty("type")
    public String getViolationType() { return violationType; }

    @Override
    @JsonProperty("watch_name")
    public String getWatchName() { return watchName; }

    @Override
    @JsonProperty("issue_id")
    public String getIssueId() { return issueId; }

    @Override
    @JsonProperty("references")
    public List<String> getReferences() { return references; }

    @Override
    @JsonProperty("fail_build")
    public boolean getFailBuild() { return failBuild; }

    @Override
    @JsonProperty("license_key")
    public String getLicenseKey() { return licenseKey; }

    @Override
    @JsonProperty("license_name")
    public String getLicenseName() { return licenseName; }

    @Override
    @JsonProperty("ignore_url")
    public String getIgnoreUrl() { return ignoreUrl; }
}
