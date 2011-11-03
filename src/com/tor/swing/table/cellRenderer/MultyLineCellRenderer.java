package com.tor.swing.table.cellRenderer;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.08.2008
 * Time: 11:07:00
 * @version 1.00
 * To change this template use File | Settings | File Templates.
 */
public class MultyLineCellRenderer extends JTextArea implements TableCellRenderer {
    private static final Logger log = Logger.getLogger(MultyLineCellRenderer.class);

    {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));

            if (table.isCellEditable(row, column)) {
                setForeground(UIManager.getColor("Table.focusCellForeground"));
                setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else setBorder(new EmptyBorder(1, 2, 1, 2));
        setFont(table.getFont());
        setText((value == null) ? "" : value.toString());
        return this;
    }
}
