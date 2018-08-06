package com.verygoodsecurity.samples.controller;

import com.verygoodsecurity.samples.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

  private final SomeService someService;

  @Autowired
  public SomeController(SomeService someService) {
    this.someService = someService;
  }
}
