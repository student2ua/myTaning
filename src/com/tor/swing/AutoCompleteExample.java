package com.tor.swing;

import com.tor.swing.jcombobox.AutoComplete;
import com.tor.swing.jcombobox.WiderDropDownCombo;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
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


        String[] row = {"первыый", "второй", "третий", "123456789 123456789"};
        JFrame frame = new JFrame();
        AutoComplete autoComplete = new AutoComplete(new Vector(Arrays.asList(row)));
        autoComplete.setEditable(true);

        JComboBox s2 = new JComboBox(row);
//        @see WideComboBox      + https://stackoverflow.com/questions/956003/how-can-i-change-the-width-of-a-jcombobox-dropdown-list
        s2.setUI(new BasicComboBoxUI() {
            @Override
            protected ComboPopup createPopup() {
                return new BasicComboPopup(comboBox) {
                    protected Rectangle computePopupBounds(int px, int py, int pw, int ph) {
                        return super.computePopupBounds(px, py, 500, ph);
                    }
                };
            }
        });
        JComboBox s3 = new JComboBox(row);
        s3.addPopupMenuListener(getPPL());
        WiderDropDownCombo s4 = new WiderDropDownCombo(row);
        s4.setWide(true);

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().add(autoComplete);
        frame.getContentPane().add(s2);
        frame.getContentPane().add(s3);
        frame.getContentPane().add(s4);
        frame.setSize(136, 137);
//        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //+ https://tips4java.wordpress.com/2010/11/28/combo-box-popup/
    private static PopupMenuListener getPPL() {
        return new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                @SuppressWarnings("unchecked") final JComboBox comboBox = (JComboBox) e.getSource();

                final BasicComboPopup popup = (BasicComboPopup) comboBox.getAccessibleContext().getAccessibleChild(0);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //Get the scroll pane from the popup list
                        JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, popup.getList());

                        //Get the width of the popup list
                        int popupWidth = popup.getList().getPreferredSize().width;

                        int widest = getWidestJComboBox(comboBox);
                        //Set the scrollpane to that size
                        Dimension scrollPaneSize = scrollPane.getPreferredSize();
                        scrollPaneSize.width = Math.max(popupWidth, widest);
                        scrollPane.setPreferredSize(scrollPaneSize);
                        scrollPane.setMaximumSize(scrollPaneSize);

                        //Change the location or the preferred size doesn't update
                        try {
                            Point location = comboBox.getLocationOnScreen();
                            int height = comboBox.getPreferredSize().height;
                            popup.setLocation(location.x, location.y + height - 1);
                            popup.setLocation(location.x, location.y + height);
                        } catch (IllegalComponentStateException exc) {
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }

        };
    }

    private static int getWidestJComboBox(JComboBox comboBox) {
        int widest = 95;
        Font font = comboBox.getFont();
        FontMetrics metrics = comboBox.getFontMetrics(font);
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Object item = comboBox.getItemAt(i);
            int lineWidth = metrics.stringWidth(item.toString());
            widest = Math.max(widest, lineWidth);
        }
        widest += 5;
        return widest;
    }
}
