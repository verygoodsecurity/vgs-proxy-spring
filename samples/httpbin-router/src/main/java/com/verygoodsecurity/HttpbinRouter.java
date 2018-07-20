package com.verygoodsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HttpbinRouter {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpbinRouter.class);

  private RestTemplate restTemplate;

  @Autowired
  public HttpbinRouter(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @PostMapping(value = "/post")
  @ResponseBody
  public String send(@RequestBody final String json) {
    LOGGER.info("Redacted JSON using VGS reverse proxy:\n {}", json);

    final HttpEntity<String> entity = new HttpEntity<>(json);
    final String response = restTemplate
        .exchange("https://httpbin.verygoodsecurity.io/post", HttpMethod.POST, entity, String.class)
        .getBody();

    LOGGER.info("Revealed JSON after sending via VGS forward proxy:\n {}", response);

    return response;
  }

}
