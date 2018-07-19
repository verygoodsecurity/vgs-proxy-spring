package com.verygood.security.demo;


import com.verygoodsecurity.spring.annotation.EnableVgsProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVgsProxy
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
