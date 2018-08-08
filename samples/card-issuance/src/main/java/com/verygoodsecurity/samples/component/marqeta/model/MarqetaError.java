package com.verygoodsecurity.samples.component.marqeta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

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
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
