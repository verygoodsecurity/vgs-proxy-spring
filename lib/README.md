# vgs-proxy-spring
VGS integration with Spring framework

# Info

This library includes:

* Forward proxy RestTemplate configuration (RestTemplate BeanPostProcessor)

**Build library to use it locally**

# Dependency #

```
#!xml

<dependency>
  <groupId>com.verygood.security</groupId>
  <artifactId>vgs-proxy-spring</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>


```

# Integration #

1) Checkout repo
2) ```mvn clean install```
3) Add dependency to your project 
4) Add property **vgs.forward.proxy.host** - which is "Forward proxy url" from dashboard.
5) Add @EnableVGS annotation on top of your @Configuration
6) Autowire VgsRestTemplate in your service 
```java
@Autowired
private VgsRestTemplate restTemplate;
``` 
7) When you will use it for HTTP calls - requests will go through proxy.

