package com.verygood.security.integration.proxy.annotation;

import com.verygood.security.integration.proxy.config.VgsClientConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(value = VgsClientConfiguration.class)
public @interface EnableVGS {

}
