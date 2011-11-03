package com.tor.util.serializ;

public class CompositeBean {
    private SimpleBean s;

    public SimpleBean getS() {
        return s;
    }

    public void setS(SimpleBean s) {
        this.s = s;
    }

    public String toString() {
        return String.valueOf(getS().getName());
//        return String.format("[%s] %s", this.getClass().getName(), getS().getName());
    }
}