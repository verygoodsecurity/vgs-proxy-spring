package com.verygoodsecurity.samples.component.marqeta;

import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardRequest;
import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardResponse;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserRequest;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class MarqetaClient {

  private static final Logger logger = LoggerFactory.getLogger(MarqetaClient.class);

  private final RestTemplate restTemplate;

  private String baseUrl = "https://shared-sandbox-api.marqeta.com/v3";

  @Autowired
  public MarqetaClient(@Qualifier("marqetaRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public CreateCardResponse createCard(CreateCardRequest request) {
    try {
      final String url = baseUrl + "/cards?show_pan=true&show_cvv_number=true";
      return restTemplate.postForObject(url, request, CreateCardResponse.class);
    } catch (HttpStatusCodeException cause) {
      // TODO: Add proper error handling
      logger.error(cause.getResponseBodyAsString());
      throw cause;
    }
  }

  public CreateUserResponse createUser(CreateUserRequest request) {
    try {
      final String url = baseUrl + "/users";
      return restTemplate.postForObject(url, request, CreateUserResponse.class);
    } catch (HttpStatusCodeException cause) {
      // TODO: Add proper error handling
      logger.error(cause.getResponseBodyAsString());
      throw cause;
    }
  }
}
