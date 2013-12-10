package com.tor.sun.net;

import java.util.Map;

/**
 * User: tor
 * Date: 10.12.13
 * Time: 20:15
 * http://dev64.wordpress.com/2012/10/07/convinient-object-builder-for-testing/
 */
public class HttpRequest {
    public final String POST = "POST";
    public final String GET = "GET";

    public final String method;
    public final String url;

    public final Map<String, String> headers;

    public final byte[] content;

    public HttpRequest(String method, String url, Map<String, String> headers, byte[] content) {
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.content = content;
    }
}
