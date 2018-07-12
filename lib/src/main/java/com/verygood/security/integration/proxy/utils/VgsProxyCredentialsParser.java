package com.verygood.security.integration.proxy.utils;

import com.verygood.security.integration.proxy.exception.VgsForwardProxyUrlFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VgsProxyCredentialsParser {

  private static Pattern usernamePattern = Pattern.compile("//(.*?):");
  private static Pattern passwordPattern = Pattern.compile(":([^:]+)@");
  private static Pattern hostPattern = Pattern.compile("@(.*?):");
  private static Pattern portPattern = Pattern.compile("(\\d+)(?!.*\\d)");

  public static VgsProxyCredentials parseForwardProxyLink(final String forwardProxyLink){
    return VgsProxyCredentials.builder()
        .username(findUsername(forwardProxyLink))
        .password(findPassword(forwardProxyLink))
        .proxyHost(findProxyHost(forwardProxyLink))
        .proxyPort(Integer.parseInt(findProxyPort(forwardProxyLink)))
        .build();
  }

  private static String findUsername(final String stringWithRegex){
    Matcher usernameMatcher = usernamePattern.matcher(stringWithRegex);
    if (usernameMatcher.find()) {
      return usernameMatcher.group(1);
    } else {
      throw new VgsForwardProxyUrlFormatException("Wrong format of forward proxy property. Cant find username.");
    }
  }

  private static String findPassword(final String stringWithRegex){
    Matcher passwordMatcher = passwordPattern.matcher(stringWithRegex);
    if (passwordMatcher.find()) {
      return passwordMatcher.group(1);
    } else {
      throw new VgsForwardProxyUrlFormatException("Wrong format of forward proxy property. Cant find password.");
    }
  }

  private static String findProxyHost(final String stringWithRegex){
    Matcher proxyHostMatcher = hostPattern.matcher(stringWithRegex);
    if (proxyHostMatcher.find()) {
      return proxyHostMatcher.group(1);
    } else {
      throw new VgsForwardProxyUrlFormatException("Wrong format of forward proxy property. Cant find proxy host.");
    }
  }

  private static String findProxyPort(final String stringWithRegex){
    Matcher portMatcher = portPattern.matcher(stringWithRegex);
    if (portMatcher.find()) {
      return portMatcher.group(1);
    } else {
      throw new VgsForwardProxyUrlFormatException("Wrong format of forward proxy property. Cant find proxy port.");
    }
  }
}
