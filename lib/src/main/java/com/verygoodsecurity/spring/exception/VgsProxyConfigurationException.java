package com.verygoodsecurity.spring.exception;

/**
 * Thrown to indicate that the VGS forward proxy cannot be configured properly,
 * with the most likely cause of it being a malformed proxy URL.
 */
public class VgsProxyConfigurationException extends RuntimeException {

  public VgsProxyConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }
}
