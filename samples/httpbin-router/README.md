# Httpbin Router
Sample application demonstrating use of [vgs-proxy-spring](https://github.com/verygoodsecurity/vgs-proxy-spring/) library.

The application is a router to [httpbin](https://httpbin.verygoodsecurity.io/) service, the values which are sent to it are redacted via reverse proxy and revealed on request to httpbin via forward proxy.

# Local setup
1. Install [ngrok](https://ngrok.com/)
2. Start ngrok with `ngrok http 8080`
3. Go to [VGS Dashboard](https://dashboard.verygoodsecurity.com/)
4. Create sandbox vault and configure the routes the following way (use your ngrok address for Inbound destination url):

<img src="https://github.com/verygoodsecurity/vgs-proxy-spring/blob/master/samples/httpbin-router/httpbin_routes.png" alt="Httpbin routes on VGS Dashboard" width=768 >

5. Add your forward proxy url to `application.properties`
6. Start the `httpbin-router` app with `mvn spring-boot:run`
7. Send a curl request to verify request is proxied via VGS:

```
curl https://tntgssc4gig.SANDBOX.verygoodproxy.com/post \
  -H "Content-type: application/json" \
  -d '{"secret": "foo"}'
```

8. Check the logs for redacted `secret` value using VGS proxy:

```
2018-07-20 12:24:01.974  INFO 88278 --- [nio-8080-exec-1] com.verygoodsecurity.HttpBinRouter       : Redacted JSON using VGS reverse proxy:
 {"secret":"tok_sandbox_dSDFUJwihGCSVwcsKWEJfa"}
```

9. Check the logs for revealed `secret` value using VGS proxy:

```
2018-07-20 12:24:03.531  INFO 88278 --- [nio-8080-exec-1] com.verygoodsecurity.HttpBinRouter       : Revealed JSON after sending via VGS forward proxy:
 {
  "args": {}, 
  "data": "{\"secret\":\"foo\"}", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Accept": "text/plain, application/json, application/*+json, */*",  
    "Accept-Encoding": "gzip,deflate", 
    "Connection": "close", 
    "Content-Length": "16", 
    "Content-Type": "text/plain;charset=ISO-8859-1", 
    "Host": "httpbin.verygoodsecurity.io", 
    "User-Agent": "Apache-HttpClient/4.5.2 (Java/1.8.0_151)", 
    "Vgs-Request-Id": "cc0cbf4e84bc3fef", 
    "Via": "1.1 3c455ebab049"
  }, 
  "json": {
    "secret": "foo"
  }, 
  "origin": "52.6.216.177, 172.31.2.130", 
  "url": "https://httpbin.verygoodsecurity.io/post"
}
```
