package com.tor.localase;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.10.2009
 * Time: 15:12:59
 * To change this template use File | Settings | File Templates.
 */
public class Localese {
    private static final Logger log = Logger.getLogger(Localese.class);

    public Localese() {
    }

    public static void main(String[] args) {
        Locale currentLocale = new Locale("RU");
        String hi = ResourceBundle.getBundle("com.tor.localase.Localese", currentLocale).getString("hi");
        System.out.println("Locale RU = " + hi);
        currentLocale = new Locale("UA");

        hi = ResourceBundle.getBundle("com.tor.localase.Localese", currentLocale).getString("hi");
        System.out.println("Locale UA = " + hi);
    }
}
