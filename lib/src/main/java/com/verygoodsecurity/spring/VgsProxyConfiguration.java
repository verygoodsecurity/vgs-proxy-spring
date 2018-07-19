package com.verygoodsecurity.spring;

import com.verygoodsecurity.spring.annotation.VgsProxied;
import com.verygoodsecurity.spring.exception.VgsProxyConfigurationException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.ssl.SSLContexts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Collections;
import java.util.List;

@Configuration
public class VgsProxyConfiguration {

  private static final String TRUST_STORE_RESOURCE_NAME = "certs/vgs-proxy.jks";
  private static final String TRUST_STORE_PASSWORD = "verygoodproxy";

  private final UriComponents forwardProxyUri;

  private List<RestTemplate> restTemplates = Collections.emptyList();

  @Autowired
  public VgsProxyConfiguration(@Value("${vgs.proxy.url}") String forwardProxyUrl) {
    try {
      this.forwardProxyUri = UriComponentsBuilder.fromHttpUrl(forwardProxyUrl).build();
    } catch (Exception cause) {
      throw new VgsProxyConfigurationException("Failed to parse proxy URL", cause);
    }
  }

  @Autowired(required = false)
  public void setRestTemplates(@VgsProxied List<RestTemplate> restTemplates) {
    this.restTemplates = restTemplates;
  }

  @PostConstruct
  public void configureRestTemplates() {
    if (restTemplates != null) {
      final ClientHttpRequestFactory requestFactory = createRequestFactory();
      for (RestTemplate restTemplate : restTemplates) {
        restTemplate.setRequestFactory(requestFactory);
      }
    }
  }

  public ClientHttpRequestFactory createRequestFactory() {
    final SSLContext sslContext = buildSSLContext();

    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(
        new AuthScope(
            forwardProxyUri.getHost(),
            forwardProxyUri.getPort()
        ),
        parseProxyCredentials()
    );

    return new HttpComponentsClientHttpRequestFactory(
        HttpClients.custom()
            .useSystemProperties()
            .setSSLContext(sslContext)
            .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
            .setDefaultCredentialsProvider(credentialsProvider)
            .build()
    );
  }

  private SSLContext buildSSLContext() {
    final ClassLoader classLoader = getClass().getClassLoader();
    try {
      final KeyStore vgsProxyCerts = KeyStore.getInstance(KeyStore.getDefaultType());
      try (InputStream inputStream = classLoader.getResourceAsStream(TRUST_STORE_RESOURCE_NAME)) {
        vgsProxyCerts.load(inputStream, TRUST_STORE_PASSWORD.toCharArray());
      }
      return SSLContexts.custom().loadTrustMaterial(vgsProxyCerts, null).build();
    } catch (Exception cause) {
      throw new VgsProxyConfigurationException("Failed to build SSL context", cause);
    }
  }

  private UsernamePasswordCredentials parseProxyCredentials() {
    final String userInfo = forwardProxyUri.getUserInfo();
    try {
      final int colonIndex = userInfo.indexOf(':');
      return new UsernamePasswordCredentials(userInfo.substring(0, colonIndex), userInfo.substring(colonIndex + 1));
    } catch (Exception cause) {
      throw new VgsProxyConfigurationException("Failed to parse proxy credentials", cause);
    }
  }
}
