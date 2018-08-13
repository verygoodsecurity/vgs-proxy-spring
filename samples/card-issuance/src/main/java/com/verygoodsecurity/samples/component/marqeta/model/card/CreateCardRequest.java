package com.verygoodsecurity.samples.component.marqeta.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

// https://www.marqeta.com/api/docs/VhypzxwAANwA_Vgx/cards#create_card
public class CreateCardRequest {

  @JsonProperty("card_product_token")
  private String cardProductToken;

  @JsonProperty("user_token")
  private String userToken;

  public CreateCardRequest() {
  }

  public CreateCardRequest(String cardProductToken, String userToken) {
    this.cardProductToken = cardProductToken;
    this.userToken = userToken;
  }

  public String getCardProductToken() {
    return cardProductToken;
  }

  public void setCardProductToken(String cardProductToken) {
    this.cardProductToken = cardProductToken;
  }

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
