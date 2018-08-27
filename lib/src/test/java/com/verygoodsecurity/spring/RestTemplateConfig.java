package com.verygoodsecurity.spring;

import com.verygoodsecurity.spring.annotation.EnableVgsProxy;
import com.verygoodsecurity.spring.annotation.VgsProxied;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableVgsProxy
public class RestTemplateConfig {

  @Bean
  @VgsProxied
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
