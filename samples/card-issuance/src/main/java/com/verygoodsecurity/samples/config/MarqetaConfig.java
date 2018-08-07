package com.verygoodsecurity.samples.config;

import com.verygoodsecurity.spring.annotation.EnableVgsProxy;
import com.verygoodsecurity.spring.annotation.VgsProxied;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(MarqetaApiKeys.class)
@EnableVgsProxy
public class MarqetaConfig {

  private final MarqetaApiKeys apiKeys;

  @Autowired
  public MarqetaConfig(MarqetaApiKeys apiKeys) {
    this.apiKeys = apiKeys;
  }

  @Bean
  @VgsProxied
  public RestTemplate marqetaRestTemplate(RestTemplateBuilder restTemplateBuilder) {
    final RestTemplate restTemplate = restTemplateBuilder.build();
    restTemplate.getInterceptors().add(authorization());
    return restTemplate;
  }

  private ClientHttpRequestInterceptor authorization() {
    return new BasicAuthorizationInterceptor(apiKeys.getApplicationToken(), apiKeys.getMasterAccessToken());
  }
}
