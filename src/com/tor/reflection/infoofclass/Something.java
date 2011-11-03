package com.tor.reflection.infoofclass;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.01.2009
 * Time: 16:26:28
 * To change this template use File | Settings | File Templates.
 */
public final class Something {
    public transient String variable;

    public synchronized String getVariable() {
        return this.variable;
    }

    protected Something() {
        this.variable = "var";
    }
}