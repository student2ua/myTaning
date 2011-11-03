package com.tor.reflection.proxy;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.01.2009
 * Time: 15:06:42
 * To change this template use File | Settings | File Templates.
 */
public class Name implements IName {
    private static final Logger log = Logger.getLogger(Name.class);
    private String name;

    public void setName(String name) {
        this.name = name;

    }

    public String getName() {

        return name;
    }
}
