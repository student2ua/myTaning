package com.tor.reflection.proxy;

import org.apache.log4j.Logger;

import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.01.2009
 * Time: 15:27:31
 */
public class INameProxyFactory {
    private static final Logger log = Logger.getLogger(INameProxyFactory.class);
    public static final Class[] interfaces = new Class[]{IName.class};

    public static IName createProxyInUpCase() {
        ProxyNameInUpCase proxyNameInUpCase = new ProxyNameInUpCase(new Name());

        return (IName) Proxy.newProxyInstance(IName.class.getClassLoader(),
                interfaces, proxyNameInUpCase);
    }
}
