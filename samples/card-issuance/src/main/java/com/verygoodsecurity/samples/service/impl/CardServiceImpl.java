package com.verygoodsecurity.samples.service.impl;

import com.verygoodsecurity.samples.component.marqeta.MarqetaClient;
import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardRequest;
import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardResponse;
import com.verygoodsecurity.samples.domain.Card;
import com.verygoodsecurity.samples.domain.repository.CardRepository;
import com.verygoodsecurity.samples.service.CardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

  private final CardRepository cardRepository;
  private final MarqetaClient marqetaClient;

  private final String cardProductToken;

  public CardServiceImpl(CardRepository cardRepository, MarqetaClient marqetaClient,
      @Value("${marqeta.card-product.token}") String cardProductToken) {

    this.cardRepository = cardRepository;
    this.marqetaClient = marqetaClient;

    this.cardProductToken = cardProductToken;
  }

  @Override
  public Card createCard(String userToken) {
    final CreateCardResponse response = marqetaClient.createCard(new CreateCardRequest(cardProductToken, userToken));

    final Card card = new Card();
    card.setToken(response.getToken());
    card.setCardProductToken(response.getCardProductToken());
    card.setUserToken(response.getUserToken());
    card.setPan(response.getPan());
    card.setCvvNumber(response.getCvvNumber());
    card.setExpirationTime(response.getExpirationTime());

    return cardRepository.save(card);
  }
}
