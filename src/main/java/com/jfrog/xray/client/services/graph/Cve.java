package com.jfrog.xray.client.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Cve {
    @JsonProperty("cve")
    String getId();
    @JsonProperty("cvss_v2_score")
    String getCvssV2Score();
    @JsonProperty("cvss_v2_vector")
    String getCvssV2Vector();
    @JsonProperty("cvss_v3_score")
    String getCvssV3Score();
    @JsonProperty("cvss_v3_vector")
    String getCvssV3Vector();

}
