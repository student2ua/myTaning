package com.tor.swing.renderers;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.12.2010
 * Time: 14:34:03
 * добавление иконки в рендерер
 * jComboBox.setRenderer(new InstanceWithIconCellRendererWrapper(jComboBox.getRenderer()));
 */
public class InstanceWithIconCellRendererWrapper implements ListCellRenderer {

    private final ListCellRenderer wrapped;

    public InstanceWithIconCellRendererWrapper(ListCellRenderer listCellRenderer) {
        this.wrapped = listCellRenderer;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String displayName = String.valueOf(value); // customize here
        Component renderer = wrapped.getListCellRendererComponent(list, displayName, index, isSelected, cellHasFocus);
        if (renderer instanceof JLabel) {
            Icon icon = new ImageIcon(); // customize here
            ((JLabel) renderer).setIcon(icon);
        }
        return renderer;
    }
}
