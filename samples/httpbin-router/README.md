# Httpbin Router
Sample application demonstrating use of [vgs-proxy-spring](https://github.com/verygoodsecurity/vgs-proxy-spring/) library.
The application is a router to [httpbin](https://httpbin.verygoodsecurity.io/) service, the values which are sent to it are redacted via reverse proxy and revealed on request to httpbin via forward proxy.

# Local setup
1. Install [ngrok](https://ngrok.com/)
2. Start ngrok with `ngrok http 8080`
3. Go to [VGS Dashboard](https://dashboard.verygoodsecurity.com/)
4. Configure the routes the following way, use ngrok address for Inbound destination url:
<img src="https://github.com/verygoodsecurity/vgs-proxy-spring/blob/master/samples/httpbin-router/httpbin%20-%20routes.png" alt="Httpbin routes on VGS Dashboard" width=512 >
5. Add your forward proxy url to `application.properties`
6. Start the `httpbin-router` app with `mvn spring-boot:run`
7. Send a curl request to verify request is proxied via VGS:
```
curl https://tntgssc4gig.SANDBOX.verygoodproxy.com/post \                                                                                            ✔  8844  12:11:13
  -H "Content-type: application/json" \
  -d '{"secret": "foo"}'
```
8. Check the logs for redacted value using VGS proxy:
```
2018-07-20 12:24:01.974  INFO 88278 --- [nio-8080-exec-1] com.verygoodsecurity.HttpBinRouter       : Redacted JSON using VGS reverse proxy:
 {"secret":"tok_sandbox_dSDFUJwihGCSVwcsKWEJfa"}
```
9. Check the logs for revealed value using VGS proxy:
```
2018-07-20 12:24:03.531  INFO 88278 --- [nio-8080-exec-1] com.verygoodsecurity.HttpBinRouter       : Revealed JSON after sending via VGS forward proxy:
 {
  "args": {}, 
  "data": "{\"secret\":\"foo\"}", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Accept": "text/plain, application/json, application/*+json, */*", 
    "Accept-Charset": "big5, big5-hkscs, cesu-8, euc-jp, euc-kr, gb18030, gb2312, gbk, ibm-thai, ibm00858, ibm01140, ibm01141, ibm01142, ibm01143, ibm01144, ibm01145, ibm01146, ibm01147, ibm01148, ibm01149, ibm037, ibm1026, ibm1047, ibm273, ibm277, ibm278, ibm280, ibm284, ibm285, ibm290, ibm297, ibm420, ibm424, ibm437, ibm500, ibm775, ibm850, ibm852, ibm855, ibm857, ibm860, ibm861, ibm862, ibm863, ibm864, ibm865, ibm866, ibm868, ibm869, ibm870, ibm871, ibm918, iso-2022-cn, iso-2022-jp, iso-2022-jp-2, iso-2022-kr, iso-8859-1, iso-8859-13, iso-8859-15, iso-8859-2, iso-8859-3, iso-8859-4, iso-8859-5, iso-8859-6, iso-8859-7, iso-8859-8, iso-8859-9, jis_x0201, jis_x0212-1990, koi8-r, koi8-u, shift_jis, tis-620, us-ascii, utf-16, utf-16be, utf-16le, utf-32, utf-32be, utf-32le, utf-8, windows-1250, windows-1251, windows-1252, windows-1253, windows-1254, windows-1255, windows-1256, windows-1257, windows-1258, windows-31j, x-big5-hkscs-2001, x-big5-solaris, x-compound_text, x-euc-jp-linux, x-euc-tw, x-eucjp-open, x-ibm1006, x-ibm1025, x-ibm1046, x-ibm1097, x-ibm1098, x-ibm1112, x-ibm1122, x-ibm1123, x-ibm1124, x-ibm1166, x-ibm1364, x-ibm1381, x-ibm1383, x-ibm300, x-ibm33722, x-ibm737, x-ibm833, x-ibm834, x-ibm856, x-ibm874, x-ibm875, x-ibm921, x-ibm922, x-ibm930, x-ibm933, x-ibm935, x-ibm937, x-ibm939, x-ibm942, x-ibm942c, x-ibm943, x-ibm943c, x-ibm948, x-ibm949, x-ibm949c, x-ibm950, x-ibm964, x-ibm970, x-iscii91, x-iso-2022-cn-cns, x-iso-2022-cn-gb, x-iso-8859-11, x-jis0208, x-jisautodetect, x-johab, x-macarabic, x-maccentraleurope, x-maccroatian, x-maccyrillic, x-macdingbat, x-macgreek, x-machebrew, x-maciceland, x-macroman, x-macromania, x-macsymbol, x-macthai, x-macturkish, x-macukraine, x-ms932_0213, x-ms950-hkscs, x-ms950-hkscs-xp, x-mswin-936, x-pck, x-sjis_0213, x-utf-16le-bom, x-utf-32be-bom, x-utf-32le-bom, x-windows-50220, x-windows-50221, x-windows-874, x-windows-949, x-windows-950, x-windows-iso2022jp", 
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
