package com.tor.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 15:11
 * http://www.tutorialspoint.com/ejb/ejb_interceptors.htm
 */
public class BusinessInterceptor {
    @AroundInvoke
    public Object methodInterceptor(InvocationContext context) throws Exception {
        System.out.println("*** Intercepting call to LibraryBean method: " +
                context.getMethod().getName());
        return context.proceed();
    }
}
