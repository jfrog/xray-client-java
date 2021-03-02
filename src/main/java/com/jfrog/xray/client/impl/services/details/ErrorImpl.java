package com.jfrog.xray.client.impl.services.details;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jfrog.xray.client.services.details.Error;

/**
 * @author yahavi
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class ErrorImpl implements Error {
    private String errorCode;
    private String message;

    @JsonProperty("error_code")
    public String getErrorCode() {
        return errorCode;
    }

    @JsonProperty("error_message")
    public String getMessage() {
        return message;
    }
}
