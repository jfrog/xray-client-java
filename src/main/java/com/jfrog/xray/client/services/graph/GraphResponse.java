package com.jfrog.xray.client.services.graph;

import java.io.Serializable;
import java.util.List;

public interface GraphResponse extends Serializable {
    String getScanId();

    String getPackageType();

    List<? extends Violation> getViolations();

    List<? extends Vulnerability> getVulnerabilities();

    List<? extends License> getLicenses();
}
