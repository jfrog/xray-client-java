package com.jfrog.xray.client.services.details;

import com.jfrog.xray.client.services.summary.Artifact;

import java.io.Serializable;
import java.util.List;

/**
 * @author yahavi
 **/
public interface DetailsResponse extends Serializable {
    String getBuildName();

    String getBuildNumber();

    boolean getScanCompleted();

    List<? extends Artifact> getComponents();

    Error getError();
}
