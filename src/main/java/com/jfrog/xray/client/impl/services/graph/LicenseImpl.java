package com.jfrog.xray.client.impl.services.graph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.graph.Component;
import com.jfrog.xray.client.services.graph.License;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LicenseImpl implements License {

    private String name;
    private String key;
    private Map<String, ComponentImpl> components = null;

    @Override
    @JsonProperty("license_name")
    public String getName() {
        return name;
    }

    @Override
    @JsonProperty("license_key")
    public String getKey() { return key; }

    @Override
    @JsonProperty("components")
    public Map<String, ? extends Component> getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(Map<String, ComponentImpl> components) {
        this.components = components;
    }
}