package com.tor.reflection.proxy;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.01.2009
 * Time: 15:09:14
 * Пример патена прокси. преобразуем  String к капсу
 * по спец заказу для Блондинок.
 * работаем в обе стороны
 */
public class ProxyNameInUpCase implements InvocationHandler {
    private static final Logger log = Logger.getLogger(ProxyNameInUpCase.class);
    private IName name;

    public ProxyNameInUpCase(IName name) {
        this.name = name;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args != null)
            for (int indexArg = 0; indexArg < args.length; indexArg++) {
                if (args[indexArg] instanceof String) {
                    /** Собственно преобразование*/
                    args[indexArg] = ((String) args[indexArg]).toUpperCase();
                }
            }
        Object rezult = method.invoke(this.name, args);
        System.out.println("Method '" + method.getName() + "' has been invoked");
        return rezult;
    }
}