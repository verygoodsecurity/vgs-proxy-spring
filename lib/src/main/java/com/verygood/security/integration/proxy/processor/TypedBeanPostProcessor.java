package com.verygood.security.integration.proxy.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public abstract class TypedBeanPostProcessor<T> implements BeanPostProcessor {

  private final Class<T> beanType;

  public TypedBeanPostProcessor(Class<T> beanType) {
    this.beanType = beanType;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if (this.beanType.isInstance(bean)) {
      return postProcessBeforeInitialization(this.beanType.cast(bean));
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
      throws BeansException {
    if (this.beanType.isInstance(bean)) {
      return postProcessAfterInitialization(this.beanType.cast(bean));
    }
    return bean;
  }

  protected abstract T postProcessBeforeInitialization(T bean);

  protected abstract T postProcessAfterInitialization(T bean);

}