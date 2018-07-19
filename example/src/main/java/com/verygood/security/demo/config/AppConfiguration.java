package com.verygood.security.demo.config;

import com.verygoodsecurity.spring.annotation.VgsProxied;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

  @Bean
  @VgsProxied
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
