package com.jfrog.xray.client.services.summary;

import java.io.Serializable;
import java.util.List;

/**
 * @author yahavi
 */
public interface VulnerableComponents extends Serializable {

    List<String> getFixedVersions();
}
