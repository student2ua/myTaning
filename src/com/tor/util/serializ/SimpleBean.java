package com.tor.util.serializ;

public class SimpleBean {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
//        return String.format("[%s] %s", this.getClass().getName(), getName());
        return new String(this.getClass().getName());
    }
}