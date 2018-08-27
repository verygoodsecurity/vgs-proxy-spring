package com.verygoodsecurity.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {VgsProxyConfiguration.class, RestTemplateConfig.class}, loader= AnnotationConfigContextLoader.class)
@TestPropertySource("classpath:application.properties")
public class ApplicationContextTest extends AbstractJUnit4SpringContextTests {

  @Autowired
  private ApplicationContext applicationContext;

  @Value("${vgs.proxy.url}")
  private String forwardProxyUrl;

  @Test
  public void testAppContext() {
    applicationContext.getBean(RestTemplate.class);
  }



}
