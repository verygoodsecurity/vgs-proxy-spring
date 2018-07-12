package com.verygood.security.integration.proxy.config;

import com.verygood.security.integration.proxy.client.VgsRestTemplate;
import com.verygood.security.integration.proxy.processor.VgsRestTemplatePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VgsClientConfiguration {

  @Value("${vgs.forward.proxy.host}")
  private String forwardProxyHost;

  @Bean
  public VgsRestTemplatePostProcessor restTemplatePostProcessor(){
    return new VgsRestTemplatePostProcessor(VgsRestTemplate.class, forwardProxyHost);
  }

  @Bean
  public VgsRestTemplate vgsRestTemplate(){
    return new VgsRestTemplate();
  }

}
