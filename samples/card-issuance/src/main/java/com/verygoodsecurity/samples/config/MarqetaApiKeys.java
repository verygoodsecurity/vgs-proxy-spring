package com.verygoodsecurity.samples.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "marqeta.api-keys")
public class MarqetaApiKeys {

  /**
   * TODO: Add description.
   */
  private String applicationToken;

  /**
   * TODO: Add description.
   */
  private String masterAccessToken;

  public String getApplicationToken() {
    return applicationToken;
  }

  public void setApplicationToken(String applicationToken) {
    this.applicationToken = applicationToken;
  }

  public String getMasterAccessToken() {
    return masterAccessToken;
  }

  public void setMasterAccessToken(String masterAccessToken) {
    this.masterAccessToken = masterAccessToken;
  }
}
