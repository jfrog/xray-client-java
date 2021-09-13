package com.jfrog.xray.client.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public interface License {
    @JsonProperty("key")
    String getKey();

    @JsonProperty("name")
    String getName();

    @JsonProperty("custom")
    boolean getCustom();

    @JsonProperty("references")
    List<String> getReferences();

    @JsonProperty("components")
    Map<String, ? extends Component> getComponents();

}
