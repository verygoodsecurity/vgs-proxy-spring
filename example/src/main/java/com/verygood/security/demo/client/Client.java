package com.verygood.security.demo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Client {

  private RestTemplate restTemplate;

  @Autowired
  public Client(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String testCall(final String json) {
    HttpEntity<String> entity = new HttpEntity<>(json);
    return restTemplate
        .exchange("https://httpbin.verygoodsecurity.io/post", HttpMethod.POST, entity, String.class)
        .getBody();
  }

}
