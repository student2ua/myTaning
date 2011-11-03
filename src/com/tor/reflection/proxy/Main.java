package com.tor.reflection.proxy;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.01.2009
 * Time: 15:42:14
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        IName nameProxy = INameProxyFactory.createProxyInUpCase();

        nameProxy.setName("Maxim");
        System.out.println(nameProxy.getName());

        nameProxy.setName("Helen");
        System.out.println(nameProxy.getName());

        nameProxy.setName("in lower case");
        System.out.println(nameProxy.getName());
    }
}
