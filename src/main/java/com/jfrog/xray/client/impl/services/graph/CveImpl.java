package com.jfrog.xray.client.impl.services.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.graph.Cve;

public class CveImpl implements Cve {
    private String id;
    private String cvssV2Score;
    private String cvssV2Vector;
    private String cvssV3Score;
    private String cvssV3Vector;

    @Override
    @JsonProperty("cve")
    public String getId() {
        return id;
    }

    @Override
    @JsonProperty("cvss_v2_score")
    public String getCvssV2Score() {
        return cvssV2Score;
    }

    @Override
    @JsonProperty("cvss_v2_vector")
    public String getCvssV2Vector() {
        return cvssV2Vector;
    }

    @Override
    @JsonProperty("cvss_v3_score")
    public String getCvssV3Score() {
        return cvssV3Score;
    }

    @Override
    @JsonProperty("cvss_v3_vector")
    public String getCvssV3Vector() {
        return cvssV3Vector;
    }
}
