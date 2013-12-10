package com.tor.sun.net;

import java.util.HashMap;
import java.util.Map;

/**
 * User: tor
 * Date: 10.12.13
 * Time: 19:44
 * http://dev64.wordpress.com/2012/10/07/convinient-object-builder-for-testing/
 */
public class HttpRequestBuilder {

    public static final Map<String, String> EMPTY_HEADERS =
            new HashMap<String, String>();

    public static HttpRequestBuilder request() {
        return new HttpRequestBuilder();
    }

    public static HttpRequest with(Object... objects) {
        String method = "GET";
        String url = null;
        Map<String, String> headers = EMPTY_HEADERS;
        byte[] content = null;

        for (Object object : objects) {
            if (object instanceof Method) {
                method = ((Method) object).method;
            }
            if (object instanceof Url) {
                url = ((Url) object).url;
            }
            if (object instanceof Headers) {
                headers = ((Headers) object).headers;
            }
            if (object instanceof Content) {
                content = ((Content) object).content;
            }
        }
        return new HttpRequest(method, url, headers, content);

    }

    public static Method method(String method) {
        return new Method(method);
    }

    public static Url url(String url) {
        return new Url(url);
    }

    public static Content content(byte[] content) {
        return new Content(content);
    }

    public static Headers headers(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("Number of arguments has to be even");
        }
        HashMap<String, String> headers = new HashMap<String, String>();
        for (int i = 0; i < objects.length; i += 2) {
            headers.put(objects[i].toString(), objects[i + 1].toString());
        }
        return new Headers(headers);
    }

    private static class Method {
        final String method;

        Method(String method) {
            this.method = method;
        }
    }

    private static class Url {
        final String url;

        Url(String url) {
            this.url = url;
        }
    }

    private static class Headers {
        final Map<String, String> headers;

        Headers(Map<String, String> headers) {
            this.headers = headers;
        }
    }

    private static class Content {
        final byte[] content;

        Content(byte[] content) {
            this.content = content;
        }
    }
}