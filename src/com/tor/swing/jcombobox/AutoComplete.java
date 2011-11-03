package com.tor.swing.jcombobox;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 22.10.2009
 * Time: 12:28:03
 * To change this template use File | Settings | File Templates.
 */
public class AutoComplete extends JComboBox implements JComboBox.KeySelectionManager {
    private String searchFor;
    private long lap;

    public class CBDocument extends PlainDocument {
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            super.insertString(offset, str, a);
            if (!isPopupVisible() && str.length() != 0) fireActionEvent();
        }
    }

    public AutoComplete(Vector items) {
        super(items);
        lap = new java.util.Date().getTime();
        setKeySelectionManager(this);
        JTextField tf;
        if (getEditor() != null) {
            tf = (JTextField) getEditor().getEditorComponent();
            if (tf != null) {
                tf.setDocument(new CBDocument());
                addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        JTextField tf = (JTextField) getEditor().getEditorComponent();
                        String text = tf.getText();
                        ComboBoxModel aModel = getModel();
                        String current;
                        for (int i = 0; i < aModel.getSize(); i++) {
                            current = aModel.getElementAt(i).toString();
                            if (current.toLowerCase().startsWith(text.toLowerCase())) {
                                tf.setText(current);
                                tf.setSelectionStart(text.length());
                                tf.setSelectionEnd(current.length());
                                break;
                            }
                        }
                    }
                });
            }
        }
    }

    public int selectionForKey(char aKey, ComboBoxModel aModel) {
        long now = new java.util.Date().getTime();
        if (searchFor != null && aKey == KeyEvent.VK_BACK_SPACE && searchFor.length() > 0) {
            searchFor = searchFor.substring(0, searchFor.length() - 1);
        } else {
            //	System.out.println(lap);
            // Kam nie hier vorbei.
            if (lap + 1000 < now)
                searchFor = "" + aKey;
            else
                searchFor = searchFor + aKey;
        }
        lap = now;
        String current;
        for (int i = 0; i < aModel.getSize(); i++) {
            current = aModel.getElementAt(i).toString().toLowerCase();
            if (current.toLowerCase().startsWith(searchFor.toLowerCase())) return i;
        }
        return -1;
    }

    public void fireActionEvent() {
        super.fireActionEvent();
    }
}