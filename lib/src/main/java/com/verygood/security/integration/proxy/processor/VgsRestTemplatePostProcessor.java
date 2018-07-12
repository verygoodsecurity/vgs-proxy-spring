package com.verygood.security.integration.proxy.processor;

import com.verygood.security.integration.proxy.client.VgsRestTemplate;
import com.verygood.security.integration.proxy.exception.VgsConfigurationException;
import com.verygood.security.integration.proxy.utils.VgsProxyCredentials;
import com.verygood.security.integration.proxy.utils.VgsProxyCredentialsParser;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
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
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class VgsRestTemplatePostProcessor extends TypedBeanPostProcessor<VgsRestTemplate> {

  private String forwardProxyHost;
  private VgsProxyCredentialsParser credentialsParser;

  public VgsRestTemplatePostProcessor(Class<VgsRestTemplate> beanType, String forwardProxyHost,
      VgsProxyCredentialsParser credentialsParser) {
    super(beanType);
    this.forwardProxyHost = forwardProxyHost;
    this.credentialsParser = credentialsParser;
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
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
    SSLContext sslContext = null;
    try {
      sslContext = org.apache.http.ssl.SSLContexts.custom()
          .loadTrustMaterial(null, acceptingTrustStrategy)
          .build();
    } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
      e.printStackTrace();
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
