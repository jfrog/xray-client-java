package com.jfrog.xray.client.services.summary;

/**
 * @author gonggy
 * @date 2022/6/13
 */
public enum IssueSeverity {

    CRITICAL("Critical"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low"),
    UNKNOWN("Unknown");

    private String severity;

    private IssueSeverity(String severity) {
        this.severity = severity;
    }

    public String severity() {
        return severity;
    }

    public static IssueSeverity of(String severity) {
        for (IssueSeverity is : values()) {
            if (is.severity.equalsIgnoreCase(severity)) {
                return is;
            }
        }
        return UNKNOWN;
    }
}
