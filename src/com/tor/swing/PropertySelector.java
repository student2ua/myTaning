package com.tor.swing;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;

/**
 * User: tor
 * Date: 21.06.19
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class PropertySelector extends JComboBox implements ItemListener {

    private PropertyEditor editor;
    private boolean notifyEditorOfChanges = true;

    /**
     * Constructor.
     *
     * @param pe bean property editor that is updated when the state
     *           changes in the combo box
     */
    public PropertySelector(PropertyEditor pe) {
        editor = pe;
        String tags[] = editor.getTags();
        for (int i = 0; i < tags.length; i++) {
            addItem(tags[i]);
        }

        setSelectedIndex(0);

        // This is a no-op if the getAsText is not a tag that we set from getTags() above
        setSelectedItem(editor.getAsText());
        addItemListener(this);
        invalidate();

        editor.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String value = editor.getAsText();
                if (!value.equals(getSelectedItem())) {
                    notifyEditorOfChanges = false;
                    try {
                        setSelectedItem(value);
                    } finally {
                        notifyEditorOfChanges = true;
                    }
                }
            }
        });
    }

    /*
     *  (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent evt) {
        if (notifyEditorOfChanges) {
            String s = (String) getSelectedItem();
            editor.setAsText(s);
        }
    }
}