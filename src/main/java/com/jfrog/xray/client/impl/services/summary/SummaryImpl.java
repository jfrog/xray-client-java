package com.jfrog.xray.client.impl.services.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfrog.xray.client.impl.XrayClient;
import com.jfrog.xray.client.impl.util.ObjectMapperHelper;
import com.jfrog.xray.client.services.summary.Components;
import com.jfrog.xray.client.services.summary.Summary;
import com.jfrog.xray.client.services.summary.SummaryResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by romang on 2/27/17.
 */
public class SummaryImpl implements Summary {

    private static final ObjectMapper mapper = ObjectMapperHelper.get();
    private final XrayClient xray;

    public SummaryImpl(XrayClient xray) {
        this.xray = xray;
    }

    @Override
    public SummaryResponse artifact(List<String> checksums, List<String> paths) throws IOException {
        if (checksums == null && paths == null) {
            return new SummaryResponseImpl();
        }
        return post("artifact", new ArtifactSummaryBody(checksums, paths));
    }

    @Override
    public SummaryResponse component(Components components) throws IOException {
        if (components == null) {
            return new SummaryResponseImpl();
        }
        return post("component", components);
    }

    private SummaryResponse post(String api, Object body) throws IOException {
        HttpEntity entity = null;
        try (CloseableHttpResponse response = xray.post("summary/" + api, body)) {
            entity = response.getEntity();
            return mapper.readValue(response.getEntity().getContent(), SummaryResponseImpl.class);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class ArtifactSummaryBody {

        private final List<String> checksums;
        private final List<String> paths;

        ArtifactSummaryBody(List<String> checksums, List<String> paths) {
            this.checksums = checksums;
            this.paths = paths;
        }

        @SuppressWarnings("unused")
        @JsonProperty("checksums")
        public List<String> getChecksums() {
            return checksums;
        }

        @SuppressWarnings("unused")
        @JsonProperty("paths")
        public List<String> getPaths() {
            return paths;
        }
    }

}
