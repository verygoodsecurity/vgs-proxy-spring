package com.verygood.security.integration.proxy.ssl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class VeryGoodTrustManager implements X509TrustManager {

  public static final VeryGoodTrustManager INSTANCE;

  static {
    try {
      INSTANCE = new VeryGoodTrustManager();
    } catch (Exception cause) {
      throw new ExceptionInInitializerError(cause);
    }
  }

  private final X509TrustManager jsseTrustManager;
  private final X509TrustManager vgsProxyTrustManager;

  private VeryGoodTrustManager() throws Exception {
    final TrustManagerFactory trustManagerFactory = getDefaultTrustManagerFactory();
    trustManagerFactory.init((KeyStore) null);
    this.jsseTrustManager = findFirstX509TrustManager(trustManagerFactory.getTrustManagers());
    trustManagerFactory.init(loadVgsProxyCerts());
    this.vgsProxyTrustManager = findFirstX509TrustManager(trustManagerFactory.getTrustManagers());
  }

  private static TrustManagerFactory getDefaultTrustManagerFactory() throws Exception {
    return TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
  }

  @Override
  public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    try {
      jsseTrustManager.checkClientTrusted(chain, authType);
    } catch (CertificateException ignored) {
      vgsProxyTrustManager.checkClientTrusted(chain, authType);
    }
  }

  @Override
  public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    try {
      jsseTrustManager.checkServerTrusted(chain, authType);
    } catch (CertificateException ignored) {
      vgsProxyTrustManager.checkServerTrusted(chain, authType);
    }
  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return ArrayUtils.addAll(jsseTrustManager.getAcceptedIssuers(), vgsProxyTrustManager.getAcceptedIssuers());
  }

  private X509TrustManager findFirstX509TrustManager(TrustManager[] trustManagers) {
    return Arrays.stream(trustManagers)
        .filter(trustManager -> trustManager instanceof X509TrustManager)
        .map(trustManager -> (X509TrustManager) trustManager)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No X509TrustManager found"));
  }

  private KeyStore loadVgsProxyCerts() throws Exception {
    final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    InputStream inputStream = null;
    try {
      inputStream = getClass().getClassLoader().getResourceAsStream("certs/vgs-proxy.jks");
      keyStore.load(inputStream, "verygoodproxy".toCharArray());
    } finally {
      IOUtils.closeQuietly(inputStream);
    }
    return keyStore;
  }
}
