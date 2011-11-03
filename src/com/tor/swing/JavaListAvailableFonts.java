package com.tor.swing;

import org.apache.log4j.Logger;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.03.2009
 * Time: 14:13:11
 * To change this template use File | Settings | File Templates.
 */
public class JavaListAvailableFonts {
    private static final Logger log = Logger.getLogger(JavaListAvailableFonts.class);

    public static void main(String[] args) {
        String[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for(int it=0;it<fonts.length;it++){
            System.out.println(it+") " + fonts[it]);
        }
     }
}
