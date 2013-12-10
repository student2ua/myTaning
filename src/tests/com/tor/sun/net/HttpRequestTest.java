package com.tor.sun.net;

import org.junit.Assert;
import org.junit.Test;

import static com.tor.sun.net.HttpRequestBuilder.*;

/**
 * User: tor
 * Date: 10.12.13
 * Time: 20:17
 * <code>HttpRequest request = request().with(
 * url("http://test.site.com"),
 * method("POST"),
 * headers(
 * "Content-type", "text/xml",
 * ),
 * content("<?xml version="1.0"?>< request>Test request</ request>");
 * );
 * </code>
 */
public class HttpRequestTest {
    @Test
    public void test() {
        HttpRequest request = request().with(
                url("http://www.google.com"),
                headers(
                        "X-TEST1", "test value1",
                        "X-TEST2", "test value1"
                )
        );

        Assert.assertEquals("http://www.google.com", request.url);
        Assert.assertEquals("test value1", request.headers.get("X-TEST1"));
        Assert.assertEquals("GET", request.method);
    }
}
