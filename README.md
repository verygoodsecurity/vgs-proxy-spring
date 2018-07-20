<p align="center"><a href="https://www.verygoodsecurity.com/"><img src="https://avatars0.githubusercontent.com/u/17788525" width="128" alt="VGS Logo"></a></p>
<p align="center"><b>vgs-proxy-spring</b></p>
<p align="center"><i>Provides an easy way to integrate a Spring-based project with the VGS forward proxy.</i></p>

## Quick Start

### Installation

Before you begin, add this library as a dependency to your project.

For Maven:

```xml
<dependency>
    <groupId>com.verygoodsecurity</groupId>
    <artifactId>vgs-proxy-spring</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

For Gradle:

```
compile 'com.verygoodsecurity:vgs-proxy-spring:1.0-SNAPSHOT'
```

### Usage

First of all, set the `vgs.proxy.url` property in the application context for
the vault you want to integrate with. Look for the `Outbound Route URL` on the
`Integration` tab within [the
dashboard](https://dashboard.verygoodsecurity.com/dashboard). It should look
similar to this one:

    https://USpoCb9aUrdxkXi8Puh9PfjA:48b2e5a6-e846-4f8e-b6df-a71b2c010cf3@tnttzeeizod.SANDBOX.verygoodproxy.com:8080

Secondly, do the following steps to configure a `RestTemplate` to use the VGS
forward proxy:

- Declare a bean annotated with `@VgsProxied`
- Annotate any configuration class with `@EnableVgsProxy`

Here's an example configuration you may end up with:

```java
@Configuration
@EnableVgsProxy
public class AppConfig {

    @Bean
    @VgsProxied
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

Of course, not every HTTP request should be proxied, so you may also have a
`RestTemplate` that is not configured to use the proxy. Simply declare another
bean and inject it as normal:

```java
@Configuration
@EnableVgsProxy
public class AppConfig {

    @Bean
    @VgsProxied
    public RestTemplate vgsProxied() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

However, you must use a qualifier to access the proxied `RestTemplate` then:

```java
@Component
public class MySecureClient {

  private final RestTemplate restTemplate;
  private final RestTemplate vgsProxied;

  @Autowired
  public MySecureClass(RestTemplate restTemplate,
                       @VgsProxied RestTemplate vgsProxied) {

    this.restTemplate = restTemplate;
    this.vgsProxied = vgsProxied;
  }

  public void doRegularStuff() {
    return restTemplate.getForObject("https://httpbin.verygoodsecurity.io/get", String.class);
  }

  public void doSecureStuff(String json) {
    vgsProxied.postForObject("https://httpbin.verygoodsecurity.io/post", json, String.class);
  }
}
```

For a complete example, see [the sample project](samples/httpbin-router).

## What is VGS?

_**Want to just jump right in?** Check out our [getting started
guide](https://www.verygoodsecurity.com/docs/getting-started)._

Very Good Security (VGS) allows you to enhance your security standing while
maintaining the utility of your data internally and with third-parties. As an
added benefit, we accelerate your compliance certification process and help you
quickly obtain security-related compliances that stand between you and your
market opportunities.

To learn more, visit us at https://www.verygoodsecurity.com/

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file
for details.
