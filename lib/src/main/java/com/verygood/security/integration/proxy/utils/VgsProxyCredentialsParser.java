package com.verygood.security.integration.proxy.utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class VgsProxyCredentialsParser {

  private static final String USER_INFO_SEPARATOR = ":";

  private UriComponents uriComponents;

  public VgsProxyCredentials parseForwardProxyLink(final String forwardProxyLink) {
    this.uriComponents = UriComponentsBuilder.fromUriString(forwardProxyLink).build();

    return VgsProxyCredentials.builder()
        .username(withUsername())
        .password(withPassword())
        .proxyHost(uriComponents.getHost())
        .proxyPort(uriComponents.getPort())
        .build();
  }

  private String withUsername() {
    return uriComponents.getUserInfo().split(USER_INFO_SEPARATOR)[0];
  }

  private String withPassword() {
    return uriComponents.getUserInfo().split(USER_INFO_SEPARATOR)[1];
  }
}
