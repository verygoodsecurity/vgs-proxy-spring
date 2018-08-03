package com.verygoodsecurity.samples.httpbin;


import com.verygoodsecurity.spring.annotation.EnableVgsProxy;
import com.verygoodsecurity.spring.annotation.VgsProxied;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableVgsProxy
public class Application {

  @Bean
  @VgsProxied
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
