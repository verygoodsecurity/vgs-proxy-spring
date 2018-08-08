package com.verygoodsecurity.samples.web.controller.advice;

import com.verygoodsecurity.samples.component.marqeta.model.MarqetaError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MarqetaErrorControllerAdvice {

  private static final Logger logger = LoggerFactory.getLogger(MarqetaErrorControllerAdvice.class);

  @ExceptionHandler(MarqetaError.class)
  public ResponseEntity<MarqetaError> handleMarqetaError(MarqetaError exception) {
    logger.debug("Handling Marqeta error: {}", exception);
    return ResponseEntity.status(exception.getStatusCode()).body(exception);
  }
}
