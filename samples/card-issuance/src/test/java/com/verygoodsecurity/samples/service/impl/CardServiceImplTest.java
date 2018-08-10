package com.verygoodsecurity.samples.service.impl;

import com.verygoodsecurity.samples.component.marqeta.MarqetaClient;
import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardRequest;
import com.verygoodsecurity.samples.component.marqeta.model.card.CreateCardResponse;
import com.verygoodsecurity.samples.domain.Card;
import com.verygoodsecurity.samples.domain.repository.CardRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceImplTest {

  private static final String CARD_PRODUCT_TOKEN = UUID.randomUUID().toString();

  private CardServiceImpl target;

  @Mock
  private CardRepository cardRepository;

  @Mock
  private MarqetaClient marqetaClient;

  @Before
  public void setUp() {
    target = new CardServiceImpl(cardRepository, marqetaClient, CARD_PRODUCT_TOKEN);

    when(cardRepository.save(any())).then(invocation -> {
      final Card entity = invocation.getArgument(0);
      entity.setId(RandomUtils.nextLong());
      return entity;
    });
  }

  @Test
  public void shouldCallMarqetaApiAndSaveResponse() {
    final String userToken = "test";

    final CreateCardResponse response = new CreateCardResponse();
    response.setToken(UUID.randomUUID().toString());
    response.setCardProductToken(CARD_PRODUCT_TOKEN);
    response.setUserToken(userToken);
    response.setPan("1111222233334444");
    response.setCvvNumber("123");
    response.setExpirationTime(LocalDateTime.now().plusYears(4));

    when(marqetaClient.createCard(any())).thenReturn(response);

    final Card entity = target.createCard(userToken);

    assertThat(entity, is(notNullValue()));
    assertThat(entity.getToken(), is(equalTo(response.getToken())));
    assertThat(entity.getCardProductToken(), is(equalTo(response.getCardProductToken())));
    assertThat(entity.getUserToken(), is(equalTo(response.getUserToken())));
    assertThat(entity.getPan(), is(equalTo(response.getPan())));
    assertThat(entity.getCvvNumber(), is(equalTo(response.getCvvNumber())));
    assertThat(entity.getExpirationTime(), is(equalTo(response.getExpirationTime())));

    final ArgumentCaptor<CreateCardRequest> arg = ArgumentCaptor.forClass(CreateCardRequest.class);
    verify(marqetaClient).createCard(arg.capture());

    final CreateCardRequest request = arg.getValue();

    assertThat(request, is(notNullValue()));
    assertThat(request.getCardProductToken(), is(equalTo(CARD_PRODUCT_TOKEN)));
    assertThat(request.getUserToken(), is(equalTo(userToken)));
  }
}
