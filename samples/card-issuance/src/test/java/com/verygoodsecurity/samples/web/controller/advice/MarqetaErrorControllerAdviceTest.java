package com.verygoodsecurity.samples.web.controller.advice;

import com.verygoodsecurity.samples.component.marqeta.model.MarqetaError;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MarqetaErrorControllerAdviceTest {

  private final MarqetaErrorControllerAdvice target = new MarqetaErrorControllerAdvice();

  @Test
  public void shouldRespondWithErrorContentAsJson() {
    final MarqetaError exception = new MarqetaError();
    exception.setStatusCode(HttpStatus.BAD_REQUEST);
    exception.setErrorCode("400002");
    exception.setErrorMessage("Birth date cannot be a future date");

    final ResponseEntity<String> response = target.handleMarqetaError(exception);

    assertThat(response.getStatusCode(), is(equalTo(exception.getStatusCode())));
    assertThat(response.getHeaders().getContentType(), is(equalTo(MediaType.APPLICATION_JSON)));
    assertThat(response.getBody(), is(equalTo(exception.getMessage())));
  }
}
