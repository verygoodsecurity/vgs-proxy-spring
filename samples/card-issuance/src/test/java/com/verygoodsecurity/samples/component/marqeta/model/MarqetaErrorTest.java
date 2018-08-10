package com.verygoodsecurity.samples.component.marqeta.model;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MarqetaErrorTest {

  @Test
  public void shouldReturnMessageAsJson() {
    final String errorCode = "400002";
    final String errorMessage = "Birth date cannot be a future date";

    final MarqetaError exception = new MarqetaError();
    exception.setStatusCode(HttpStatus.BAD_REQUEST);
    exception.setErrorCode(errorCode);
    exception.setErrorMessage(errorMessage);

    final String expectedMessage = format("{\"error_code\":\"%s\",\"error_message\":\"%s\"}", errorCode, errorMessage);
    assertThat(exception.getMessage(), is(equalTo(expectedMessage)));
  }
}
