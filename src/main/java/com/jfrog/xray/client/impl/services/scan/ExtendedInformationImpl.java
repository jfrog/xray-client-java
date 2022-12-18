package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.scan.ExtendedInformation;
import com.jfrog.xray.client.services.scan.SeverityReasons;

/**
 * @author yahavi
 **/
public class ExtendedInformationImpl implements ExtendedInformation {
    public SeverityReasonsImpl[] jfrogResearchSeverityReasons;
    public String jfrogResearchSeverity;
    public String shortDescription;
    public String fullDescription;
    public String remediation;

    @Override
    @JsonProperty("short_description")
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    @JsonProperty("full_description")
    public String getFullDescription() {
        return fullDescription;
    }

    @Override
    @JsonProperty("remediation")
    public String getRemediation() {
        return remediation;
    }

    @Override
    @JsonProperty("jfrog_research_severity")
    public String getJFrogResearchSeverity() {
        return jfrogResearchSeverity;
    }

    @Override
    @JsonProperty("jfrog_research_severity_reasons")
    public SeverityReasons[] getJFrogResearchSeverityReasons() {
        return jfrogResearchSeverityReasons;
    }
}
