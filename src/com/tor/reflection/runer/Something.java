package com.tor.reflection.runer;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 14.01.2009
 * Time: 14:01:33
 * To change this template use File | Settings | File Templates.
 */
public class Something {
    private static final Logger log = Logger.getLogger(Something.class);
    public static String STATIC_VARITABLE;
    public String hello;
    public String word;
    public boolean isLoweCase;

    public String printHello(String secondWord) {
        String rez = this.hello + secondWord;
        // System.out.println(hello);
        return this.isLoweCase ? rez.toLowerCase() : rez;
    }

    public static String concatenate(String first, int second, Double third) {
        return first + " " + second + " " + third;
    }

    public Something(String hello, String word, boolean loweCase) {
        this.hello = hello;
        this.word = word;
        isLoweCase = loweCase;
    }
}
