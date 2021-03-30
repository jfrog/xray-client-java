package com.jfrog.xray.client.impl.services.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.summary.General;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralImpl implements General {

    private String name;
    private String path;
    private String sha1;
    private String sha256;
    @JsonProperty("parent_sha256")
    private List<String> parentSha256;
    @JsonProperty("pkg_type")
    private String pkgType;
    @JsonProperty("component_id")
    private String componentId;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("pkg_type")
    public String getPkgType() {
        return pkgType;
    }

    @JsonProperty("sha1")
    public String getSha1() {
        return sha1;
    }

    @JsonProperty("sha256")
    public String getSha256() {
        return sha256;
    }

    @JsonProperty("parent_sha256")
    public List<String> getParentSha256() {
        return parentSha256;
    }

    @JsonProperty("component_id")
    public String getComponentId() {
        return componentId;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public void setPath(String path) {
        this.path = path;
    }

    @SuppressWarnings("unused")
    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    @SuppressWarnings("unused")
    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    @SuppressWarnings("unused")
    public void setPkgType(String pkgType) {
        this.pkgType = pkgType;
    }

    @SuppressWarnings("unused")
    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }
}