## education examples

- [commons](.\commons\readme.md) Apache Common examples
- [dbUnitExamples](.\dbUnitExemple\readme.md) dbUnit examples
- [helloword](.\helloword\readme.md) Spring example
- [jboss](.\jboss\readme.md) JBoss 6.1 F examples
- [seExample](.\seExample\spi\readmi.md) SPI example
- [webExample](.\webExample\readme.md) many java web examples

## Maven Oracle driver
- http://www.datanucleus.org/downloads/maven2/
- http://nexus.saas.hand-china.com/content/repositories/rdc/
- https://mvnrepository.com/artifact/com.oracle/ojdbc6
- https://maven.ceon.pl/artifactory/repo

## todo read
- [Handle Connection Exceptions with Retries](https://docs.oracle.com/en/cloud/paas/app-container-cloud/cache/handle-connection-exceptions-retries.html)
- [Retry or No?](https://habr.com/ru/company/alfastrah/blog/712964/)


```
private static HttpClient client = HttpClient.newHttpClient();
private static Set<Integer> retryableCodes = Set.of(HTTP_BAD_GATEWAY, HTTP_UNAVAILABLE, HTTP_GATEWAY_TIMEOUT); //расширьте по вкусу

/*
    Дописывайте свои специфичные реализации, кому что надо
 */
public static @Nullable String httpGetStringWithRetries(HttpRequest request, int retryCount, int delay) {
    Optional<HttpResponse<String>> result = httpGetWithRetries(request, retryCount, delay, false, HttpResponse.BodyHandlers.ofString());
    return result.map(HttpResponse::body).orElse(null);
}

public static <T> Optional<HttpResponse<T>> httpGetWithRetries(HttpRequest request,
                                                               int tryCount,
                                                               int delay,
                                                               boolean isExponentDelay,
                                                               HttpResponse.BodyHandler<T> bodyHandler) {
    if (tryCount < 1) {
        throw new RuntimeException("Нельзя меньше 1 попытки");
    }
    if (delay < 0) {
        throw new RuntimeException("Машину времени еще не придумали. Задержка между попытками должна быть неотрицательной.");
    }
    int retryDelay = delay;
    for (int i = 0; i < tryCount; ++i) {
        if (i > 0 && delay > 0) {
            try {
                int jitter = (int) (ThreadLocalRandom.current().nextInt() % (retryDelay * 0.2)) - (int) (retryDelay * 0.1);
                Thread.sleep(retryDelay + jitter);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        HttpResponse<T> result = null;
        try {
            result = client.send(request, bodyHandler);
        } catch (Exception e) {
            if (!(e instanceof IOException))
                throw new RuntimeException(e);
        }
        if (result == null || retryableCodes.contains(result.statusCode())) {
            if (isExponentDelay)
                retryDelay = retryDelay * 2;
            continue;
        }
        return Optional.of(result);
    }
    return Optional.empty();
}
```    
### image java lib
- [TwelveMonkeys](https://github.com/haraldk/TwelveMonkeys)
- [jai-imageio]()
- [IM4Java]()
- [ JMagick]()
 