package com.jfrog.xray.client.impl.test;

import com.jfrog.xray.client.Xray;
import com.jfrog.xray.client.impl.XrayClientBuilder;
import org.jfrog.build.client.ProxyConfiguration;
import org.mockserver.integration.ClientAndServer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by romang on 2/2/17.
 */
public class XrayTestsBase {
    private static final String CLIENTTESTS_XRAY_ENV_VAR_PREFIX = "CLIENTTESTS_XRAY_";
    private static final String CLIENTTESTS_XRAY_PROPERTIES_PREFIX = "clienttests.xray.";

    Xray xray, xrayProxies;
    ClientAndServer mockServer;

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
        String accessToken = readParam(props, "token");

        // Create an xray client
        xray = (Xray) new XrayClientBuilder(null)
                .setUrl(url)
                .setAccessToken(accessToken)
                .build();

        // Create an xray client that uses proxy
        ProxyConfiguration proxyConfig = new ProxyConfiguration();
        proxyConfig.port = 8888;
        proxyConfig.host = "localhost";
        xrayProxies = (Xray) new XrayClientBuilder(null)
                .setUrl(url)
                .setAccessToken(accessToken)
                .setProxyConfiguration(proxyConfig)
                .setInsecureTls(true)
                .build();

        // Create a mock proxy server
        mockServer = ClientAndServer.startClientAndServer(8888);
    }

    @BeforeMethod
    public void beforeMethod() {
        mockServer.reset();
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
                "Failed to load test Xray instance credentials. " +
                        "Looking for System properties '" +
                        CLIENTTESTS_XRAY_PROPERTIES_PREFIX +
                        "url' and '" +
                        CLIENTTESTS_XRAY_PROPERTIES_PREFIX +
                        "token' or a properties file with those properties in classpath " +
                        "or Environment variables '" +
                        CLIENTTESTS_XRAY_ENV_VAR_PREFIX + "URL' and '" +
                        CLIENTTESTS_XRAY_ENV_VAR_PREFIX + "TOKEN'";

        Assert.fail(message);
    }

    @AfterClass
    public void clean() {
        xray.close();
        xrayProxies.close();
        mockServer.stop();
        while (!mockServer.hasStopped()) {
            System.out.println("Stopping mockServer...");
        }
    }
}
