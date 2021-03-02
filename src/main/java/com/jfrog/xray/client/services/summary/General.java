package com.jfrog.xray.client.services.summary;

import java.io.Serializable;
import java.util.List;

/**
 * Created by romang on 2/27/17.
 */
public interface General extends Serializable {

    String getName();

    String getPath();

    String getPkgType();

    String getSha1();

    String getSha256();

    List<String> getParentSha256();

    String getComponentId();
}
