package com.tor.swing.table.rowNum.n2;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 31.05.11
 * Time: 13:48
 * хреноый какойто, подправлять пришлось
 */

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class RowNumberHeader extends JTable {

    protected JTable mainTable;

    public RowNumberHeader(JTable table) {
        super();
        mainTable = table;
        setModel(new RowNumberTableModel());
        setPreferredScrollableViewportSize(getMinimumSize());
        setRowSelectionAllowed(false);
        JComponent renderer = (JComponent) getDefaultRenderer(Object.class);
        LookAndFeel.installColorsAndFont(renderer, "TableHeader.background",
                "TableHeader.foreground", "TableHeader.font");
        LookAndFeel.installBorder(this, "TableHeader.cellBorder");
    }

    public int getRowHeight(int row) {
        return mainTable.getRowHeight();
    }

    class RowNumberTableModel extends AbstractTableModel {

        public int getRowCount() {
            return mainTable.getModel().getRowCount();
        }

        public int getColumnCount() {
            return mainTable.getColumnCount() + 1;
        }

        public Object getValueAt(int row, int column) {
            if (column == 0) {
                return new Integer(row + 1);
            } else return mainTable.getModel().getValueAt(row, column - 1);
        }

    }

    public static void main(String[] a) {
        JFrame f = new JFrame();
        f.getContentPane().add(new RowNumberHeader(new JTable(new Object[][]{{11, 12, 13}, {21, 22, 23}}, new String[]{"A", "B", "C"})));
        f.setSize(300, 300);
        f.setVisible(true);
    }
}