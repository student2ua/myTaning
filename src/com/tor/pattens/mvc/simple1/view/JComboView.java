package com.tor.pattens.mvc.simple1.view;

import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 23.03.2009
 * Time: 18:49:42
 * To change this template use File | Settings | File Templates.
 */
public class JComboView extends JComboBox {
    private static final Logger log = Logger.getLogger(JComboView.class);
    private final String[] data = {"kom1", "kom2", "kom3"};
    private final DefaultComboBoxModel model = new DefaultComboBoxModel();

    public JComboView() {
        super();
        for (int it = 0; it < data.length; it++) {
            model.addElement(data[it]);
        }
        this.setModel(model);
    }

    public void addData(String text) {
        this.model.addElement(text);
    }

}
