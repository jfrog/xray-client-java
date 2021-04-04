package com.jfrog.xray.client.impl.util;

import org.apache.commons.codec.net.URLCodec;
import org.jfrog.build.util.URI;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;
import static org.apache.commons.codec.binary.StringUtils.newStringUsAscii;

public class URIUtil {

    public static String concatUrl(String url1, String url2) {
        if (url1.endsWith("/") && url2.startsWith("/")) {
            return url1 + url2.substring(1);
        }
        if (url1.endsWith("/") || url2.startsWith("/")) {
            return url1 + url2;
        }
        return url1 + "/" + url2;
    }

    /**
     * Escape and encode a given string with allowed characters not to be
     * escaped and a given charset.
     *
     * @param unescaped a string
     * @return the escaped string
     */
    public static String encode(String unescaped) {
        byte[] rawData = URLCodec.encodeUrl(URI.allowed_query, getBytesUtf8(unescaped));
        return newStringUsAscii(rawData);
    }
}

