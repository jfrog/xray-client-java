package com.jfrog.xray.client.services.details;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author yahavi
 **/
public interface Details extends Serializable {
    DetailsResponse build(String buildName, String buildNumber) throws IOException;
    DetailsResponse build(String buildName, String buildNumber, String project) throws IOException;
}
