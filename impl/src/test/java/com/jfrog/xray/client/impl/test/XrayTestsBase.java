package com.jfrog.xray.client.impl.test;

import com.jfrog.xray.client.Xray;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.jfrog.xray.client.impl.XrayClient.create;

/**
 * Created by romang on 2/2/17.
 */
public class XrayTestsBase {
    private static final String CLIENTTESTS_XRAY_ENV_VAR_PREFIX = "CLIENTTESTS_XRAY_";
    private static final String CLIENTTESTS_XRAY_PROPERTIES_PREFIX = "clienttests.xray.";

    Xray xray;

    @BeforeClass
    public void init() throws IOException {

        Properties props = new Properties();
        // This file is not in GitHub. Create your own in src/test/resources.
        InputStream inputStream = this.getClass().getResourceAsStream("/xray-client.properties");
        if (inputStream != null) {
            props.load(inputStream);
        }

        String url = readParam(props, "url");
        if (!url.endsWith("/")) {
            url += "/";
        }
        String username = readParam(props, "username");
        String password = readParam(props, "password");

        xray = create(url, username, password);
    }

    private String readParam(Properties props, String paramName) {
        String paramValue = null;
        if (props.size() > 0) {
            paramValue = props.getProperty(CLIENTTESTS_XRAY_PROPERTIES_PREFIX + paramName);
        }
        if (paramValue == null) {
            paramValue = System.getProperty(CLIENTTESTS_XRAY_PROPERTIES_PREFIX + paramName);
        }
        if (paramValue == null) {
            paramValue = System.getenv(CLIENTTESTS_XRAY_ENV_VAR_PREFIX + paramName.toUpperCase());
        }
        if (paramValue == null) {
            failInit();
        }
        return paramValue;
    }

    private void failInit() {
        String message =
                "Failed to load test Artifactory instance credentials. " +
                        "Looking for System properties '" +
                        CLIENTTESTS_XRAY_PROPERTIES_PREFIX +
                        "url', " +
                        CLIENTTESTS_XRAY_PROPERTIES_PREFIX +
                        "username' and " +
                        CLIENTTESTS_XRAY_PROPERTIES_PREFIX +
                        "password' or a properties file with those properties in classpath " +
                        "or Environment variables '" +
                        CLIENTTESTS_XRAY_ENV_VAR_PREFIX + "URL', " +
                        CLIENTTESTS_XRAY_ENV_VAR_PREFIX + "USERNAME' and " +
                        CLIENTTESTS_XRAY_ENV_VAR_PREFIX + "PASSWORD'";

        Assert.fail(message);
    }

    @AfterClass
    public void clean() {
        xray.close();
    }
}
