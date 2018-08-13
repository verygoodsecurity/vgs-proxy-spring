package com.verygoodsecurity.samples.service;

import com.verygoodsecurity.samples.domain.Card;

public interface CardService {

  Card createCard(String userToken);
}
