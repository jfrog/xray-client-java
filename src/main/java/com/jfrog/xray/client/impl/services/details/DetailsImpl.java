package com.jfrog.xray.client.impl.services.details;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfrog.xray.client.impl.XrayClient;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.details.Details;
import com.jfrog.xray.client.services.details.DetailsResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.jfrog.xray.client.impl.util.URIUtil.encode;

/**
 * @author yahavi
 **/
public class DetailsImpl implements Details {
    private static final ObjectMapper mapper = ObjectMapperHelper.get();
    private final XrayClient xray;

    public DetailsImpl(XrayClient xray) {
        this.xray = xray;
    }

    @Override
    public DetailsResponse build(String buildName, String buildNumber) throws IOException {
        if (StringUtils.isAnyBlank(buildName, buildNumber)) {
            return new DetailsResponseImpl();
        }
        String url = String.format("details/build?build_name=%s&build_number=%s", encode(buildName), encode(buildNumber));
        HttpEntity entity = null;
        try (CloseableHttpResponse response = xray.get(url)) {
            entity = response.getEntity();
            return mapper.readValue(response.getEntity().getContent(), DetailsResponseImpl.class);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }
}
