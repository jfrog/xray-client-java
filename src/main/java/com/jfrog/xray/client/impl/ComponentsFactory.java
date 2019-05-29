package com.jfrog.xray.client.impl;

import com.jfrog.xray.client.impl.services.summary.ComponentsImpl;
import com.jfrog.xray.client.services.summary.ComponentDetail;
import com.jfrog.xray.client.services.summary.Components;

import java.util.Set;

/**
 * Created by romang on 6/1/17.
 */
public class ComponentsFactory {

    public static Components create(Set<ComponentDetail> componentDetails) {
        return new ComponentsImpl(componentDetails);
    }

    public static Components create() {
        return new ComponentsImpl();
    }
}
