package com.jfrog.xray.client.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author yahavi
 **/
public interface SeverityReasons {

    @JsonProperty("name")
    String getName();

    @JsonProperty("description")
    String getDescription();

    @JsonProperty("is_positive")
    boolean isPositive();
}
