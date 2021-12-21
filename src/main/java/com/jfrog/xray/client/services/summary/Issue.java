package com.jfrog.xray.client.services.summary;

import com.jfrog.xray.client.services.common.Cve;

import java.io.Serializable;
import java.util.List;

/**
 * Created by romang on 2/27/17.
 */
public interface Issue extends Serializable {

    String getIssueId();

    String getSummary();

    String getSeverity();

    List<? extends VulnerableComponents> getVulnerableComponents();

    List<? extends Cve> getCves();
}
