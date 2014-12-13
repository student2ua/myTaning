package com.tor.pattens.mvc.simple1.view;

import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 23.03.2009
 * Time: 18:35:19
 * To change this template use File | Settings | File Templates.
 */
public class ListView extends JList {
    private static final Logger log = Logger.getLogger(ListView.class);
    private String[] data = {"1", "2", "3"};
    private DefaultListModel model = new DefaultListModel();

    public ListView() {
        super();
        for (int iter = 0; iter < data.length; iter++) {
            model.addElement(data[iter]);
        }
        this.setModel(model);
    }

    public void addData(String txt) {
        this.model.addElement(txt);
    }
}
