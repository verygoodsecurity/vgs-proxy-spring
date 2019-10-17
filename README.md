<p align="center"><a href="https://www.verygoodsecurity.com/"><img src="https://avatars0.githubusercontent.com/u/17788525" width="128" alt="VGS Logo"></a></p>
<p align="center"><b>vgs-proxy-spring</b><br/>Provides an easy way to integrate a Spring-based project with the VGS forward proxy.</p>
<p align="center"><a href="https://circleci.com/gh/verygoodsecurity/vgs-proxy-spring/tree/master"><img src="https://circleci.com/gh/verygoodsecurity/vgs-proxy-spring/tree/master.svg?style=svg&circle-token=a588c965f1abd47026b3beb97b004b396d009c77" alt="CircleCI"></a></p>

## Quick Start

### Installation

Before you begin, add this library as a dependency to your project.

For Maven:

```xml
<repositories>
    <repository>
        <id>vgs-oss</id>
        <name>vgs-oss</name>
        <url>https://dl.bintray.com/vg/vgs-oss</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.verygoodsecurity</groupId>
    <artifactId>vgs-proxy-spring</artifactId>
    <version>1.0.0</version>
</dependency>
```

For Gradle:

```
repositories {
    maven {
        url "https://dl.bintray.com/vg/vgs-oss"
    }
}
```

```
compile 'com.verygoodsecurity:vgs-proxy-spring:1.0.0'
```

### Usage

First of all, set the `vgs.proxy.url` property in the application context for
the vault you want to integrate with. Look for the `Outbound Route URL` on the
`Integration` tab within [the
dashboard](https://dashboard.verygoodsecurity.com/dashboard) and replace the
placeholders with a pair of [access
credentials](https://www.verygoodsecurity.com/docs/settings/access-credentials).
The resulting URL should look similar to this one:

    https://USvWJyqzunxnW1pDKxgvPLmf:3da78204-e566-4e03-a03a-d84e3d1d4d1b@tntabeiyol.SANDBOX.verygoodproxy.com:8080

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
  public MySecureClient(RestTemplate restTemplate,
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
