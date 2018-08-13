package com.verygoodsecurity.samples.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

// See https://www.marqeta.com/api/guides/WIlA2isAAMkAsk6F/quick-start#step_____get_access
@ConfigurationProperties(prefix = "marqeta.api-keys")
public class MarqetaApiKeys {

  /**
   * Application token.
   */
  private String applicationToken;

  /**
   * Master access token.
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
