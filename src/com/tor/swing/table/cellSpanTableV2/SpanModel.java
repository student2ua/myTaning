package com.tor.swing.table.cellSpanTableV2;

/**
 * User: tor
 * Date: 25.11.13
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public interface SpanModel {
    public CellSpan getCellSpanAt(int rowIndex, int columnIndex);

    public boolean isCellSpanOn();
}
