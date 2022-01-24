package com.jfrog.xray.client.services.scan;

import java.io.Serializable;
import java.util.List;

public interface GraphResponse extends Serializable {
    String getScanId();

    String getPackageType();

    int getProgressPercentage();

    List<? extends Violation> getViolations();

    List<? extends Vulnerability> getVulnerabilities();

    List<? extends License> getLicenses();
}
