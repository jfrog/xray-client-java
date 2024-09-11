package com.jfrog.xray.client.impl.xsc.types;


public class ScanRequest {

    protected ScanEventType eventType;
    protected ScanEventStatus eventStatus;
    protected String product;
    protected String productVersion;
    protected String jpdVersion;
    protected String jfrogUser;
    protected String osPlatform;
    protected String osArchitecture;
    protected String machineId;
    protected String analyzerManagerVersion;
    protected Boolean isDefaultConfig;


    public ScanRequest(ScanEventType eventType, ScanEventStatus eventStatus, String product,
                       String productVersion, String jpdVersion, String jfrogUser,
                       String osPlatform, String osArchitecture, String machineId,
                       String analyzerManagerVersion, Boolean isDefaultConfig) {
        this.eventType = eventType;
        this.eventStatus = eventStatus;
        this.product = product;
        this.productVersion = productVersion;
        this.jpdVersion = jpdVersion;
        this.jfrogUser = jfrogUser;
        this.osPlatform = osPlatform;
        this.osArchitecture = osArchitecture;
        this.machineId = machineId;
        this.analyzerManagerVersion = analyzerManagerVersion;
        this.isDefaultConfig = isDefaultConfig;
    }


    @Override
    public String toString() {
        return "{" +
                "\"event_type\":" + this.eventType.getType() + "," +
                "\"event_status\":\"" + this.eventStatus.getStatus() + "\"," +
                "\"product\":\"" + this.product + "\"," +
                "\"product_version\":\"" + this.productVersion + "\"," +
                "\"jpd_version\":\"" + this.jpdVersion + "\"," +
                "\"jfrog_user\":\"" + this.jfrogUser + "\"," +
                "\"os_platform\":\"" + this.osPlatform + "\"," +
                "\"os_architecture\":\"" + this.osArchitecture + "\"," +
                "\"is_default_config\":" + this.isDefaultConfig + "," +
                "\"machine_id\":\"" + this.machineId + "\"," +
                "\"analyzer_manager_version\":\"" + this.analyzerManagerVersion + "\"" +
                "}";
    }
}
