package com.verygood.security.demo;


import com.verygood.security.integration.proxy.annotation.EnableVgs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVgs
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
