package com.tor.swing.jtabbetpane;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.11.2010
 * Time: 17:14:46
 * To change this template use File | Settings | File Templates.
 */
public class CloseTabbedPane extends JTabbedPane {
    private TabCloseUI closeUI;

    public CloseTabbedPane() {
        addTab("TAB 1 ", new JPanel());
        closeUI = new TabCloseUI();
        closeUI.setTabbedPane(this);
        addMouseMotionListener(closeUI);
        addMouseListener(closeUI);
    }

    public void paint(Graphics g) {
        super.paint(g);
        closeUI.paint(g);
    }
}
