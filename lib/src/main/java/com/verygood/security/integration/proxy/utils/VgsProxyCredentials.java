package com.verygood.security.integration.proxy.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VgsProxyCredentials {

  private String username;
  private String password;
  private String proxyHost;
  private Integer proxyPort;

}
