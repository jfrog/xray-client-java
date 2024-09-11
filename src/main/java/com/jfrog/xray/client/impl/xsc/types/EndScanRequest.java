package com.jfrog.xray.client.impl.xsc.types;

public class EndScanRequest extends ScanRequest {
    private Integer totalFindings;
    private Integer totalIgnoredFindings;
    private Integer totalScanDuration;
    private String multiScanId;

    public EndScanRequest(
            ScanEventType eventType,
            ScanEventStatus eventStatus,
            String product,
            String productVersion,
            String jpdVersion,
            String jfrogUser,
            String osPlatform,
            String osArchitecture,
            String machineId,
            String analyzerManagerVersion,
            Boolean isDefaultConfig,
            Integer totalFindings,
            Integer totalIgnoredFindings,
            Integer totalScanDuration,
            String multiScanId) {
        super(eventType, eventStatus, product, productVersion, jpdVersion, jfrogUser, osPlatform, osArchitecture, machineId, analyzerManagerVersion, isDefaultConfig);
        this.totalScanDuration = totalScanDuration;
        this.totalFindings = totalFindings;
        this.totalIgnoredFindings = totalIgnoredFindings;
        this.multiScanId = multiScanId;
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
                "\"analyzer_manager_version\":\"" + this.analyzerManagerVersion + "\"," +
                "\"total_findings\":" + totalFindings + "," +
                "\"total_ignored_findings\":" + totalIgnoredFindings + "," +
                "\"total_scan_duration\":\"" + totalScanDuration + "\"," +
                "\"multi_scan_id\":\"" + multiScanId + "\"" +
                "}";
    }
}