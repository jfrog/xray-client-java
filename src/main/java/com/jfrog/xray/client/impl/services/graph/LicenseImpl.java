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
    private boolean custom;
    private Map<String, ComponentImpl> components = null;
    private List<String> references= null;

    @Override
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @Override
    @JsonProperty("key")
    public String getKey() { return key; }

    @Override
    @JsonProperty("custom")
    public boolean getCustom() { return custom; }

    @Override
    @JsonProperty("references")
    public List<String> getReferences() { return references; }

    @Override
    @JsonProperty("components")
    public Map<String, ? extends Component> getComponents() {
        return components;
    }

}