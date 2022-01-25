package com.jfrog.xray.client.impl.services.scan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.scan.Component;
import com.jfrog.xray.client.services.scan.License;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LicenseImpl implements License {

    private String licenseName;
    private String licenseKey;
    private Map<String, ComponentImpl> components;
    private List<String> references;

    @Override
    @JsonProperty("license_name")
    public String getLicenseName() {
        return licenseName;
    }

    @Override
    @JsonProperty("license_key")
    public String getLicenseKey() {
        return licenseKey;
    }

    @Override
    @JsonProperty("components")
    public Map<String, ? extends Component> getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(Map<String, ComponentImpl> components) {
        this.components = components;
    }

    @JsonProperty("references")
    @Override
    public List<String> getReferences() {
        return references;
    }
}