package com.verygoodsecurity.samples.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "card")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "token")
  private String token;

  @Column(name = "card_product_token")
  private String cardProductToken;

  @Column(name = "user_token")
  private String userToken;

  @Column(name = "pan")
  private String pan;

  @Column(name = "cvv_number")
  private String cvvNumber;

  @Column(name = "expiration_time")
  private LocalDateTime expirationTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
