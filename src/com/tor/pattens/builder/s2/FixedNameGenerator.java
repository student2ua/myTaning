package com.tor.pattens.builder.s2;

/**
 * User: tor
 * Date: 02.12.14
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */
public class FixedNameGenerator implements NameGenerator {
    final String name;

    public FixedNameGenerator(String name) {
        this.name = name;
    }

    @Override
    public String generate() {
        return name;
    }
}
