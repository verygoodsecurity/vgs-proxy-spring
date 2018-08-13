package com.verygoodsecurity.samples.component.marqeta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

// https://www.marqeta.com/api/docs/Vh2cTBwAAB8AF3aI/errors#error_codes_and_messages
public class MarqetaError extends RuntimeException {

  @JsonIgnore
  private HttpStatus statusCode;

  @JsonProperty("error_code")
  private String errorCode;

  @JsonProperty("error_message")
  private String errorMessage;

  public HttpStatus getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(HttpStatus statusCode) {
    this.statusCode = statusCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public String getMessage() {
    return this.toString();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("error_code", errorCode)
        .append("error_message", errorMessage)
        .build();
  }
}
