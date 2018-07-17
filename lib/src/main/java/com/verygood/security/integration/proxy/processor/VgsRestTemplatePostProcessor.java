package com.verygood.security.integration.proxy.processor;

import com.verygood.security.integration.proxy.client.VgsRestTemplate;
import com.verygood.security.integration.proxy.exception.VgsConfigurationException;
import com.verygood.security.integration.proxy.utils.VgsProxyCredentials;
import com.verygood.security.integration.proxy.utils.VgsProxyCredentialsParser;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class VgsRestTemplatePostProcessor extends TypedBeanPostProcessor<VgsRestTemplate> {

  private static final String TRUST_STORE_RESOURCE_NAME = "certs/vgs-proxy.jks";
  private static final String TRUST_STORE_PASSWORD = "verygoodproxy";

  private String forwardProxyHost;
  private VgsProxyCredentialsParser credentialsParser;

  private KeyStore trustStore;

  public VgsRestTemplatePostProcessor(Class<VgsRestTemplate> beanType, String forwardProxyHost,
      VgsProxyCredentialsParser credentialsParser) {
    super(beanType);
    this.forwardProxyHost = forwardProxyHost;
    this.credentialsParser = credentialsParser;
  }

  @PostConstruct
  public void init() throws Exception {
    trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TRUST_STORE_RESOURCE_NAME)) {
      trustStore.load(inputStream, TRUST_STORE_PASSWORD.toCharArray());
    }
  }

  @Override
  protected VgsRestTemplate postProcessBeforeInitialization(VgsRestTemplate bean) {
    return bean;
  }

  @Override
  protected VgsRestTemplate postProcessAfterInitialization(VgsRestTemplate bean) {
    // Extracting credentials from Forward proxy URL.
    VgsProxyCredentials vgsProxyCredentials = credentialsParser
        .parseForwardProxyLink(forwardProxyHost);

    // SSL Configuration
    SSLContext sslContext;
    try {
      sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, null).build();
    } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
      throw new VgsConfigurationException("Can't configure ssl context. " + e.getMessage());
    }
    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    // Configuring proxy credentials.
    final CredentialsProvider credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(
        new AuthScope(vgsProxyCredentials.getProxyHost(), vgsProxyCredentials.getProxyPort()),
        new UsernamePasswordCredentials(vgsProxyCredentials.getUsername(),
            vgsProxyCredentials.getPassword())
    );

    // Configuring HttpClient for RestTemplate
    final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
    clientBuilder.useSystemProperties();
    clientBuilder.setProxy(
        new HttpHost(vgsProxyCredentials.getProxyHost(), vgsProxyCredentials.getProxyPort()));
    clientBuilder.setDefaultCredentialsProvider(credsProvider);
    clientBuilder.setSSLSocketFactory(csf);
    clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());

    final CloseableHttpClient client = clientBuilder.build();

    final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setHttpClient(client);
    bean.setRequestFactory(factory);

    return bean;
  }

}
