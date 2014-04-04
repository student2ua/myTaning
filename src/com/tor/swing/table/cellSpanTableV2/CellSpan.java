package com.tor.swing.table.cellSpanTableV2;

/**
 * User: tor
 * Date: 25.11.13
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class CellSpan {
    private int row = 0;
    private int column = 0;
    private int rowSpan = 0;
    private int columnSpan = 0;


    public CellSpan(int row, int column, int rowSpan, int columnSpan) {
        this.column = column;
        this.columnSpan = columnSpan;
        this.row = row;
        this.rowSpan = rowSpan;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }
}
