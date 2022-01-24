package com.jfrog.xray.client.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public interface License {
    @JsonProperty("license_key")
    String getLicenseKey();

    @JsonProperty("license_name")
    String getLicenseName();

    @JsonProperty("components")
    Map<String, ? extends Component> getComponents();

    @JsonProperty("references")
    List<String> getReferences();
}
