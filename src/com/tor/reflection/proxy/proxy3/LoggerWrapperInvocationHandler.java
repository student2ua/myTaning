package com.tor.reflection.proxy.proxy3;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * User: tor
 * Date: 030, 30.07.2023
 * Time: 19:28
 * логирование методов произвольного объекта
 * <pre>public<K, V> Map<K, V> wrappedMap(Map<K, V> map) {
 *     //noinspection unchecked
 *     return (Map<K, V>) Proxy.newProxyInstance(
 *         LoggerWrapperInvocationHandler.class.getClassLoader(),
 *         new Class[]{Map.class},
 *         new LoggerWrapperInvocationHandler<>(map));
 *         </pre>
 */
public class LoggerWrapperInvocationHandler<T> implements InvocationHandler {
    private final static Logger LOG = Logger.getLogger(LoggerWrapperInvocationHandler.class);
    private final T delegate;

    public LoggerWrapperInvocationHandler(T delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOG.info("Start executing " + method.getName());
        final long startTime = System.nanoTime();
        try {
            return method.invoke(delegate, args);
        } finally {
            final long endTime = System.nanoTime();
            LOG.info("End executing " + method.getName() +
                    " which took " + (endTime - startTime) + "ns");
        }
    }
}
