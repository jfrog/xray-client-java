package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.scan.SeverityReasons;

/**
 * @author yahavi
 **/
public class SeverityReasonsImpl implements SeverityReasons {
    public String description;
    public boolean positive;
    public String name;

    @Override
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @Override
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override
    @JsonProperty("is_positive")
    public boolean isPositive() {
        return positive;
    }
}
