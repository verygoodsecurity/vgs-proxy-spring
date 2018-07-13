package com.verygood.security.integration.proxy.config;

import com.verygood.security.integration.proxy.client.VgsRestTemplate;
import com.verygood.security.integration.proxy.processor.VgsRestTemplatePostProcessor;
import com.verygood.security.integration.proxy.utils.VgsProxyCredentialsParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VgsClientConfiguration {

  @Value("${vgs.forward.proxy.host}")
  private String forwardProxyHost;

  @Bean
  public VgsRestTemplatePostProcessor restTemplatePostProcessor(
      final VgsProxyCredentialsParser credentialsParser) {
    return new VgsRestTemplatePostProcessor(VgsRestTemplate.class, forwardProxyHost, credentialsParser);
  }

  @Bean
  public VgsRestTemplate vgsRestTemplate() {
    return new VgsRestTemplate();
  }

  @Bean
  public VgsProxyCredentialsParser vgsProxyCredentialsParser() {
    return new VgsProxyCredentialsParser();
  }

}
