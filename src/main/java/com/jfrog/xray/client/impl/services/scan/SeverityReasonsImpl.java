package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.scan.SeverityReasons;

/**
 * @author yahavi
 **/
public class SeverityReasonsImpl implements SeverityReasons {
    @JsonProperty("description")
    public String description;
    @JsonProperty("is_positive")
    public boolean isPositive;
    @JsonProperty("name")
    public String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isPositive() {
        return isPositive;
    }
}
