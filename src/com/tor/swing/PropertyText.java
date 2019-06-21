package com.tor.swing;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;

/**
 * User: tor
 * Date: 21.06.19
 * Time: 15:32
 * https://www.programcreek.com/java-api-examples/?code=etomica/etomica/etomica-master/etomica-core/src/main/java/etomica/graphics/PropertyText.java#
 */
public class PropertyText extends JTextField implements KeyListener, FocusListener, PropertyChangeListener {

    private transient PropertyEditor editor;
    private boolean isEditing = false;

    public PropertyText(PropertyEditor pe) {
        super(pe.getAsText());
        editor = pe;
        addKeyListener(this);
        addFocusListener(this);
        editor.addPropertyChangeListener(this);
//    	setBorder(PropertySheet.EMPTY_BORDER);
        getDocument().addDocumentListener(new UpdateDocumentListener());
    }

    public void repaint() {
    }

    protected void updateEditor() {
        isEditing = true;
        try {
            editor.setAsText(getText());
        } catch (IllegalArgumentException ex) {
            // ignore
        } finally {
            isEditing = false;
        }
    }

    /**
     * Listen to update display if editor changes value in some other way.
     * For example, display of dimensioned property values can be changed with a change of the units.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isEditing) setText(editor.getAsText());
    }

    //----------------------------------------------------------------------
    // Focus listener methods.

    public void focusGained(FocusEvent e) {
    }

    public void focusLost(FocusEvent e) {
        updateEditor();
    }

    //----------------------------------------------------------------------
    // Keyboard listener methods.

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            updateEditor();
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    private class UpdateDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateEditor();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateEditor();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateEditor();
        }


    }
}