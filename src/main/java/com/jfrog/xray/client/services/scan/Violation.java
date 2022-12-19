package com.jfrog.xray.client.services.scan;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Violation extends Vulnerability, License {
    @JsonProperty("updated")
    String getUpdated();

    @Override
    default String getEdited() {
        // Edited field is available only in vulnerabilities. It is perfectly equal to the "updated" field.
        return getUpdated();
    }

    @JsonProperty("watch_name")
    String getWatchName();

    @JsonProperty("ignore_url")
    String getIgnoreRuleUrl();
}
