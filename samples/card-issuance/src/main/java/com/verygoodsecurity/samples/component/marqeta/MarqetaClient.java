package com.verygoodsecurity.samples.component.marqeta;

import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardRequest;
import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardResponse;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserRequest;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class MarqetaClient {

  private final RestTemplate restTemplate;
  private final MarqetaErrorFactory errorFactory;

  private String baseUrl = "https://shared-sandbox-api.marqeta.com/v3";

  @Autowired
  public MarqetaClient(RestTemplate restTemplate, MarqetaErrorFactory errorFactory) {
    this.restTemplate = restTemplate;
    this.errorFactory = errorFactory;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public CreateCardResponse createCard(CreateCardRequest request) {
    try {
      final String url = baseUrl + "/cards?show_pan=true&show_cvv_number=true";
      return restTemplate.postForObject(url, request, CreateCardResponse.class);
    } catch (HttpStatusCodeException cause) {
      throw errorFactory.fromResponseBody(cause.getResponseBodyAsString(), cause.getStatusCode());
    }
  }

  public CreateUserResponse createUser(CreateUserRequest request) {
    try {
      final String url = baseUrl + "/users";
      return restTemplate.postForObject(url, request, CreateUserResponse.class);
    } catch (HttpStatusCodeException cause) {
      throw errorFactory.fromResponseBody(cause.getResponseBodyAsString(), cause.getStatusCode());
    }
  }
}
