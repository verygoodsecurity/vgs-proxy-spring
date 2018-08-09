package com.verygoodsecurity.samples.component.marqeta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verygoodsecurity.samples.component.marqeta.model.MarqetaError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JacksonAutoConfiguration.class, webEnvironment = WebEnvironment.NONE)
public class MarqetaErrorFactoryTest {

  private MarqetaErrorFactory target;

  // ObjectMapper configuration is affected by application.yml, so we need an actual
  // application context for testing correctly
  @Autowired
  private ObjectMapper objectMapper;

  @Before
  public void setUp() {
    target = new MarqetaErrorFactory(objectMapper);
  }

  @SuppressWarnings("ThrowableNotThrown")
  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfFailedToParseJson() {
    target.fromResponseBody("This string cannot be properly parsed", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  public void shouldReturnExpectedObjectIfSucceedToParseJson() {
    final HttpStatus statusCode = HttpStatus.BAD_REQUEST;

    // See https://www.marqeta.com/api/docs/Vh2cTBwAAB8AF3aI/errors#error_codes_and_messages
    final String errorCode = "400002";
    final String errorMessage = "Birth date cannot be a future date";

    final String responseBody = format("{\"error_code\":\"%s\",\"error_message\":\"%s\"}", errorCode, errorMessage);

    final MarqetaError result = target.fromResponseBody(responseBody, statusCode);

    assertThat(result, is(notNullValue()));
    assertThat(result.getStatusCode(), is(equalTo(statusCode)));
    assertThat(result.getErrorCode(), is(equalTo(errorCode)));
    assertThat(result.getErrorMessage(), is(equalTo(errorMessage)));
  }
}
