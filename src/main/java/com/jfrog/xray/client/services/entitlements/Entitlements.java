package com.jfrog.xray.client.services.entitlements;


import java.io.IOException;

public interface Entitlements {
    boolean isEntitled(Feature feature) throws IOException;
}
