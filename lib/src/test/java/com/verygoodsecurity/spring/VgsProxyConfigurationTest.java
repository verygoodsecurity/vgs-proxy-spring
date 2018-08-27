package com.verygoodsecurity.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VgsProxyConfigurationTest {

  private static final String TRUST_STORE_RESOURCE_NAME = "certs/vgs-proxy.jks";
  private static final String TRUST_STORE_PASSWORD = "verygoodproxy";
  private static final String forwardProxyUrl = "https://username:password@subdomain.SANDBOX.verygoodproxy.io:8080";

  private RestTemplate restTemplate;

  private VgsProxyConfiguration vgsProxyConfiguration;

  @Before
  public void setUp() {
    restTemplate = Mockito.mock(RestTemplate.class);
    vgsProxyConfiguration = new VgsProxyConfiguration(forwardProxyUrl);
    vgsProxyConfiguration.setRestTemplates(Collections.singletonList(restTemplate));
  }

  @Test
  public void testConfigureRestTemplates() {
    // When
    vgsProxyConfiguration.configureRestTemplates();

    // Then
    verify(restTemplate).setRequestFactory(any());
  }

  @Test
  public void testKeyStoreLoads() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
    final ClassLoader classLoader = getClass().getClassLoader();
    final KeyStore vgsProxyCerts = KeyStore.getInstance(KeyStore.getDefaultType());
    try (InputStream inputStream = classLoader.getResourceAsStream(TRUST_STORE_RESOURCE_NAME)) {
      vgsProxyCerts.load(inputStream, TRUST_STORE_PASSWORD.toCharArray());
    }
  }

}
