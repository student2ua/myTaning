package com.tor.swing;

import com.tor.swing.jcombobox.AutoComplete;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 22.10.2009
 * Time: 12:32:37
 * To change this template use File | Settings | File Templates.
 */
public class AutoCompleteExample {
    private static final Logger log = Logger.getLogger(AutoCompleteExample.class);

    public static void main(String[] args) {
        String[] row = {"ןונגûûי", "געמנמי", "ענועטי"};
        JFrame frame = new JFrame();
        AutoComplete autoComplete = new AutoComplete(new Vector(Arrays.asList(row)));
        autoComplete.setEditable(true);

        frame.getContentPane().add(autoComplete);
        //frame.setSize(300, 400);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
