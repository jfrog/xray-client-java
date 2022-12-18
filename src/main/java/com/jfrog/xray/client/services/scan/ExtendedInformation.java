package com.jfrog.xray.client.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author yahavi
 **/
public interface ExtendedInformation {
    @JsonProperty("short_description")
    String getShortDescription();

    @JsonProperty("full_description")
    String getFullDescription();

    @JsonProperty("remediation")
    String getRemediation();

    @JsonProperty("jfrog_research_severity")
    String getJFrogResearchSeverity();

    @JsonProperty("jfrog_research_severity_reasons")
    SeverityReasons[] getJFrogResearchSeverityReasons();
}
