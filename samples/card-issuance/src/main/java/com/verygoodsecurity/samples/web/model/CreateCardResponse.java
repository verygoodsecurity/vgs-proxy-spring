package com.verygoodsecurity.samples.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.verygoodsecurity.samples.domain.Card;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public class CreateCardResponse {

  @JsonProperty("token")
  private String token;

  @JsonProperty("card_product_token")
  private String cardProductToken;

  @JsonProperty("user_token")
  private String userToken;

  @JsonProperty("pan")
  private String pan;

  @JsonProperty("cvv_number")
  private String cvvNumber;

  @JsonProperty("expiration_time")
  private LocalDateTime expirationTime;

  public static CreateCardResponse fromCardEntity(Card entity) {
    final CreateCardResponse instance = new CreateCardResponse();
    instance.setToken(entity.getToken());
    instance.setCardProductToken(entity.getCardProductToken());
    instance.setUserToken(entity.getUserToken());
    instance.setPan(entity.getPan());
    instance.setCvvNumber(entity.getCvvNumber());
    instance.setExpirationTime(entity.getExpirationTime());
    return instance;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
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

  public String getPan() {
    return pan;
  }

  public void setPan(String pan) {
    this.pan = pan;
  }

  public String getCvvNumber() {
    return cvvNumber;
  }

  public void setCvvNumber(String cvvNumber) {
    this.cvvNumber = cvvNumber;
  }

  public LocalDateTime getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(LocalDateTime expirationTime) {
    this.expirationTime = expirationTime;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
