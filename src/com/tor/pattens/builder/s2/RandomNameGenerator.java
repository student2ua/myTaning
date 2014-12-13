package com.tor.pattens.builder.s2;

import java.util.UUID;

/**
 * User: tor
 * Date: 02.12.14
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */
public class RandomNameGenerator implements NameGenerator {
    @Override
    public String generate() {
         //2d7428a6-b58c-4008-8575-f05549f16316
        return UUID.randomUUID().toString();
    }
}
