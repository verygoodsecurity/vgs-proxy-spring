package com.verygoodsecurity.samples.config;

import com.verygoodsecurity.spring.annotation.EnableVgsProxy;
import com.verygoodsecurity.spring.annotation.VgsProxied;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableVgsProxy
public class VgsProxyConfig {

  @Bean
  @VgsProxied
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.build();
  }
}
