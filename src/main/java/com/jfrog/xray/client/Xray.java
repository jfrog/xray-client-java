package com.jfrog.xray.client;

import com.jfrog.xray.client.services.details.Details;
import com.jfrog.xray.client.services.graph.Graph;
import com.jfrog.xray.client.services.summary.Summary;
import com.jfrog.xray.client.services.system.System;

import java.io.Closeable;
import java.io.Serializable;

public interface Xray extends Closeable, Serializable {

    System system();

    Summary summary();

    Details details();

    Graph graph();

    @Override
    void close();
}