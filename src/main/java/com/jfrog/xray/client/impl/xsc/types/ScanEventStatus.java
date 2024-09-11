package com.jfrog.xray.client.impl.xsc.types;


public enum ScanEventStatus {
    STARTED("started"),
    COMPLETED("completed"),
    CANCELLED("cancelled"),
    FAILED("failed");

    private final String status;

    ScanEventStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }

}
