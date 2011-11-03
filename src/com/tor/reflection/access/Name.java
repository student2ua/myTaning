package com.tor.reflection.access;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 14.01.2009
 * Time: 15:53:24
 * To change this template use File | Settings | File Templates.
 */
public class Name {
    private static final Logger log = Logger.getLogger(Name.class);

    public static String DEFAULT_NAME = "Фыгня какато";

    public static String getDEFAULT_NAME() {
        return DEFAULT_NAME;
    }

    public Name(String name) {
        this.name = name;
    }

    private String name;

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

}
