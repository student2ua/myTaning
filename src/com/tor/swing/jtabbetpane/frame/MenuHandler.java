package com.tor.swing.jtabbetpane.frame;

import com.tor.swing.jtabbetpane.CloseTabbedPane;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.11.2010
 * Time: 17:28:05
 */
public class MenuHandler implements ActionListener {
    private static final Logger log = Logger.getLogger(MenuHandler.class);
    private TabFrameDemo frame;

    public MenuHandler(TabFrameDemo frame) {
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent arg0) {
        CloseTabbedPane tp = frame.getTabbedPane();
        tp.addTab("TAB " + (tp.getTabCount() + 1) + "  ", new JPanel());
    }

}
