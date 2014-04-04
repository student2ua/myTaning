package com.tor.swing.table.cellSpanTableV2;

import javax.swing.table.DefaultTableModel;

/**
 * User: tor
 * Date: 25.11.13
 * Time: 19:02
 * To change this template use File | Settings | File Templates.
 */
public class SpanTableModel extends DefaultTableModel implements SpanModel {
    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public int getRowCount() {
        return 10;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return row * column;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public CellSpan getCellSpanAt(int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            return new CellSpan(0, 0, 1, getColumnCount());
        } else {
            if (rowIndex >= 3 && rowIndex <= 5 && columnIndex >= 5 && columnIndex <= 6) {
                return new CellSpan(3, 5, 3, 2);
            }
        }
        return null;
    }

    public boolean isCellSpanOn() {
        return true;
    }
}
