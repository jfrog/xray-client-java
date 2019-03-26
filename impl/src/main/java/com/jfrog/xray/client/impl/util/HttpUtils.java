package com.jfrog.xray.client.impl.util;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * @author yahavi
 */
public class HttpUtils {

    public static void consumeResponse(HttpResponse response) {
        if (response != null) {
            EntityUtils.consumeQuietly(response.getEntity());
        }
    }
}
