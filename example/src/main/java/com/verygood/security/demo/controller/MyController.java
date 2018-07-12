package com.verygood.security.demo.controller;

import com.verygood.security.demo.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

  private Client restClient;

  public MyController(Client restClient) {
    this.restClient = restClient;
  }

  @PostMapping(value = "/post")
  @ResponseBody
  public String welcome(@RequestBody final String json) {
    LOGGER.info(json);

    final String response = restClient.testCall(json);

    LOGGER.info(response);
    return response;
  }

}
