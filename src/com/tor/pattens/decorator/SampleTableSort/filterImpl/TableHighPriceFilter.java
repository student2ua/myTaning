package com.tor.pattens.decorator.SampleTableSort.filterImpl;

import com.tor.pattens.decorator.SampleTableSort.TableFilterDecorator;
import org.apache.log4j.Logger;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 11.03.2010
 * Time: 18:42:52
 * To change this template use File | Settings | File Templates.
 */
public class TableHighPriceFilter extends TableFilterDecorator {
    private static final Logger log = Logger.getLogger(TableHighPriceFilter.class);

    // The lone constructor must be passed a reference to a
    // TableModel. This class decorates that model with additional
    // sorting functionality.
    public TableHighPriceFilter(TableModel model) {
        super(model);
        allocate();
    }

    // tableChanged is defined in TableModelListener, which
    // is implemented by TableFilterDecorator.
    public void tableChanged(TableModelEvent e) {
     //   new TableModelEvent(this, TableModelEvent.HEADER_ROW);
        allocate();
    }

    // Three TableModel methods are overridden from
    // TableModelDecorator.
    public Object getValueAt(int row, int column) {
        return getRealTableModel().getValueAt(indexes[row], column);
    }

    public void setValueAt(Object aValue, int row, int column) {
        getRealTableModel().setValueAt(aValue, row, column);
    }

    public void unFilter() {
        this.rowCount = NOT_INITIALIZED;
        allocate();
    }

    public int getRowCount() {
        return rowCount == NOT_INITIALIZED ?
                getRealTableModel().getRowCount() : rowCount;
    }

    // The filter method filters out items that cost more than
    // one dollar.
    public void filter(int column) {
        if (column != NOT_INITIALIZED) {
            int rowsNow = getRowCount();
            int[] newIndexes = new int[rowsNow];
            int newRowCount = rowsNow;
            for (int i = 0, j = 0; i < rowsNow && j < rowsNow;) {
                String price = (getRealTableModel().getValueAt(indexes[j], column)).toString();
                System.out.println("price = " + price);
                if (price.endsWith("1")) { // less than a dollar
                    newIndexes[i++] = indexes[j++];
                } else {
                    newRowCount--;
                    ++j;
                    continue;
                }
            }
            rowCount = newRowCount;
            for (int i = 0; i < rowCount; ++i)
                indexes[i] = newIndexes[i];
        }
    }

    private void allocate() {
        indexes = new int[getRowCount()];
        for (int i = 0; i < indexes.length; ++i) {
            indexes[i] = i;
        }
    }

    private int indexes[];
    private static int NOT_INITIALIZED = -1;
    private int rowCount = NOT_INITIALIZED;
}
