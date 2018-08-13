package com.verygoodsecurity.samples.component.marqeta;

import com.verygoodsecurity.samples.component.marqeta.model.MarqetaError;
import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardRequest;
import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardResponse;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserRequest;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MarqetaClientTest {

  private MarqetaClient target;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private MarqetaErrorFactory errorFactory;

  @Before
  public void setUp() {
    target = new MarqetaClient(restTemplate, errorFactory);
  }

  @SuppressWarnings("ThrowableNotThrown")
  @Test(expected = MarqetaError.class)
  public void shouldThrowMarqetaErrorOnUnsuccessfulCreateCardInvocation() {
    final HttpStatusCodeException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    doThrow(exception).when(restTemplate).postForObject(any(), any(), any(), any(Object[].class));

    when(errorFactory.fromResponseBody(any(), any())).thenReturn(new MarqetaError());

    try {
      target.createCard(new CreateCardRequest());
    } finally {
      verify(errorFactory).fromResponseBody(exception.getResponseBodyAsString(), exception.getStatusCode());
    }
  }

  @Test
  public void shouldCallMarqetaApiOnSuccessfulCreateCardInvocation() {
    final CreateCardResponse response = new CreateCardResponse();
    when(restTemplate.postForObject(any(), any(), any(), any(Object[].class))).thenReturn(response);

    final CreateCardRequest request = new CreateCardRequest();

    assertThat(target.createCard(request), is(sameInstance(response)));

    verify(restTemplate).postForObject(
        eq("https://shared-sandbox-api.marqeta.com/v3/cards?show_pan=true&show_cvv_number=true"),
        same(request),
        eq(CreateCardResponse.class),
        any(Object[].class)
    );
  }

  @SuppressWarnings("ThrowableNotThrown")
  @Test(expected = MarqetaError.class)
  public void shouldThrowMarqetaErrorOnUnsuccessfulCreateUserInvocation() {
    final HttpStatusCodeException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    doThrow(exception).when(restTemplate).postForObject(any(), any(), any(), any(Object[].class));

    when(errorFactory.fromResponseBody(any(), any())).thenReturn(new MarqetaError());

    try {
      target.createUser(new CreateUserRequest());
    } finally {
      verify(errorFactory).fromResponseBody(exception.getResponseBodyAsString(), exception.getStatusCode());
    }
  }

  @Test
  public void shouldCallMarqetaApiOnSuccessfulCreateUserInvocation() {
    final CreateUserResponse response = new CreateUserResponse();
    when(restTemplate.postForObject(any(), any(), any(), any(Object[].class))).thenReturn(response);

    final CreateUserRequest request = new CreateUserRequest();

    assertThat(target.createUser(request), is(sameInstance(response)));

    verify(restTemplate).postForObject(
        eq("https://shared-sandbox-api.marqeta.com/v3/users"),
        same(request),
        eq(CreateUserResponse.class),
        any(Object[].class)
    );
  }
}
