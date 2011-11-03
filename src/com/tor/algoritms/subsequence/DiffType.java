package com.tor.algoritms.subsequence;

import org.apache.commons.lang.enums.Enum;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.04.2010
 * Time: 18:04:27
 * To change this template use File | Settings | File Templates.
 */
public final class DiffType extends Enum {
    private static final Logger log = Logger.getLogger(DiffType.class);

    public static final DiffType ADD = new DiffType("+", "add");

    public static final DiffType REMOVE = new DiffType("-", "remove");

    public static final DiffType NONE = new DiffType(" ", "none");

    private String val;
    private String name;

    DiffType(String val, String name) {
        super(name);
        this.val = val;
        this.name = name;
    }

    //   @Override
    public String toString() {
        return val;
    }

    /* public final String getName() {
        return name;
    }*/

    public String getVal() {
        return val;
    }
}
