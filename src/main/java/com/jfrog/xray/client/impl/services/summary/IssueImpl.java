package com.jfrog.xray.client.impl.services.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.impl.services.common.CveImpl;
import com.jfrog.xray.client.services.summary.Issue;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueImpl implements Issue {

    private static final long serialVersionUID = 1L;

    @JsonProperty("issue_id")
    private String issueId;
    private String summary;
    private String severity;
    @JsonProperty("components")
    private List<VulnerableComponentsImpl> vulnerableComponents;
    private List<CveImpl> cves;

    @Override
    @JsonProperty("issue_id")
    public String getIssueId() {
        return issueId;
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
    public List<VulnerableComponentsImpl> getVulnerableComponents() {
        return vulnerableComponents;
    }

    @Override
    public List<CveImpl> getCves() {
        return cves;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Issue)) {
            return false;
        }
        return StringUtils.equals(((Issue) obj).getIssueId(), issueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueId);
    }
}