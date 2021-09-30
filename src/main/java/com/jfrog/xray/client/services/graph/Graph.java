package com.jfrog.xray.client.services.graph;


import org.jfrog.build.extractor.scan.DependencyTree;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tala on 05/09/21.
 */
public interface Graph extends Serializable {

    GraphResponse graph(DependencyTree dependencies, String projectKey) throws IOException, InterruptedException;
    GraphResponse graph(DependencyTree dependencies) throws IOException, InterruptedException;
}
