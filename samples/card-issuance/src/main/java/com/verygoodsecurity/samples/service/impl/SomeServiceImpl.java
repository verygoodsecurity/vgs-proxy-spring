package com.verygoodsecurity.samples.service.impl;

import com.verygoodsecurity.samples.domain.repository.SomeRepository;
import com.verygoodsecurity.samples.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService {

  private final SomeRepository someRepository;

  @Autowired
  public SomeServiceImpl(SomeRepository someRepository) {
    this.someRepository = someRepository;
  }
}
