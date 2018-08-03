package com.verygoodsecurity.spring.annotation;

import com.verygoodsecurity.spring.VgsProxyConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables configuration of {@link RestTemplate} beans marked with
 * {@link VgsProxied @VgsProxied} to use the VGS forward proxy.
 *
 * <p>Works together with {@link Configuration @Configuration} classes just like
 * one would expect from a similar Spring annotation:
 *
 * <pre class="code">
 * &#064;Configuration
 * &#064;EnableVgsProxy
 * public class AppConfig {
 *
 *     &#064;Bean
 *     &#064;VgsProxied
 *     public RestTemplate restTemplate() {
 *         return new RestTemplate();
 *     }
 * }
 * </pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(VgsProxyConfiguration.class)
public @interface EnableVgsProxy {
}
