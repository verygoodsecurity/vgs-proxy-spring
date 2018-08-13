package com.verygoodsecurity.samples.component.marqeta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verygoodsecurity.samples.component.marqeta.model.MarqetaError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MarqetaErrorFactory {

  private final ObjectMapper objectMapper;

  @Autowired
  public MarqetaErrorFactory(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public MarqetaError fromResponseBody(String responseBody, HttpStatus statusCode) {
    MarqetaError instance;
    try {
      instance = objectMapper.readValue(responseBody, MarqetaError.class);
    } catch (Exception cause) {
      throw new IllegalArgumentException("Failed to parse Marqeta error as JSON", cause);
    }
    instance.setStatusCode(statusCode);
    return instance;
  }
}
