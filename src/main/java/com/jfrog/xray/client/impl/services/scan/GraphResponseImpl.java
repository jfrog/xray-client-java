package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.scan.GraphResponse;
import com.jfrog.xray.client.services.scan.License;
import com.jfrog.xray.client.services.scan.Violation;
import com.jfrog.xray.client.services.scan.Vulnerability;

import java.util.List;

public class GraphResponseImpl implements GraphResponse {
    private String scanId;
    private String packageType;
    private int progressPercentage;
    private List<ViolationImpl> violations;
    private List<VulnerabilityImpl> vulnerabilities;
    private List<LicenseImpl> licenses;

    @Override
    @JsonProperty("scan_id")
    public String getScanId() {
        return scanId;
    }

    @Override
    @JsonProperty("package_type")
    public String getPackageType() {
        return packageType;
    }

    @JsonProperty("package_type")
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    @Override
    @JsonProperty("progress_percentage")
    public int getProgressPercentage() {
        return progressPercentage;
    }

    @Override
    @JsonProperty("violations")
    public List<? extends Violation> getViolations() {
        return violations;
    }

    @Override
    @JsonProperty("vulnerabilities")
    public List<? extends Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }

    @Override
    @JsonProperty("licenses")
    public List<? extends License> getLicenses() {
        return licenses;
    }

    @JsonProperty("licenses")
    public void setLicenses(List<LicenseImpl> licenses) {
        this.licenses = licenses;
    }
}
