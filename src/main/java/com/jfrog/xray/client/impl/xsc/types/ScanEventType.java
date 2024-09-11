package com.jfrog.xray.client.impl.xsc.types;


public enum ScanEventType {
    SOURCE_CODE(1);

    private final int type;

    ScanEventType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return Integer.toString(type);
    }

}
