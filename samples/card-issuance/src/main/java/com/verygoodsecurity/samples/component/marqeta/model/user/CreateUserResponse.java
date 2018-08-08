package com.verygoodsecurity.samples.component.marqeta.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CreateUserResponse {

  @JsonProperty("token")
  private String token;

  @JsonProperty("active")
  private boolean active;

  @JsonProperty("metadata")
  private Map<String, Object> metadata = new HashMap<>();

  @JsonProperty("account_holder_group_token")
  private String accountHolderGroupToken;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("email")
  private String email;

  @JsonProperty("birth_date")
  private LocalDate birthDate;

  @JsonProperty("corporate_card_holder")
  private boolean corporateCardHolder;

  @JsonProperty("ssn")
  private String ssn;

  @JsonProperty("uses_parent_account")
  private boolean usesParentAccount;

  @JsonProperty("created_time")
  private LocalDateTime createdTime;

  @JsonProperty("last_modified_time")
  private LocalDateTime lastModifiedTime;

  @JsonProperty("status")
  private Status status;

  private DepositAccount depositAccount = new DepositAccount();

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public String getAccountHolderGroupToken() {
    return accountHolderGroupToken;
  }

  public void setAccountHolderGroupToken(String accountHolderGroupToken) {
    this.accountHolderGroupToken = accountHolderGroupToken;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public boolean isCorporateCardHolder() {
    return corporateCardHolder;
  }

  public void setCorporateCardHolder(boolean corporateCardHolder) {
    this.corporateCardHolder = corporateCardHolder;
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public boolean isUsesParentAccount() {
    return usesParentAccount;
  }

  public void setUsesParentAccount(boolean usesParentAccount) {
    this.usesParentAccount = usesParentAccount;
  }

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(LocalDateTime createdTime) {
    this.createdTime = createdTime;
  }

  public LocalDateTime getLastModifiedTime() {
    return lastModifiedTime;
  }

  public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public DepositAccount getDepositAccount() {
    return depositAccount;
  }

  public void setDepositAccount(DepositAccount depositAccount) {
    this.depositAccount = depositAccount;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public enum Status {

    UNVERIFIED, LIMITED, ACTIVE, SUSPENDED, CLOSED
  }

  public static class DepositAccount {

    @JsonProperty("token")
    private String token;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("routing_number")
    private String routingNumber;

    @JsonProperty("allow_immediate_credit")
    private String allowImmediateCredit;

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }

    public String getAccountNumber() {
      return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
      return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
      this.routingNumber = routingNumber;
    }

    public String getAllowImmediateCredit() {
      return allowImmediateCredit;
    }

    public void setAllowImmediateCredit(String allowImmediateCredit) {
      this.allowImmediateCredit = allowImmediateCredit;
    }

    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
  }
}
