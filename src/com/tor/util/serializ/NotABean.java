package com.tor.util.serializ;

public class NotABean {
    private String name;

    public NotABean(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.valueOf(getName());
//        return String.format("[%s] %s", this.getClass().getName(), getName());
    }
}