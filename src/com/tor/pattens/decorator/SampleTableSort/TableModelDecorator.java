package com.tor.pattens.decorator.SampleTableSort;

import org.apache.log4j.Logger;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 14:32:10
 * To change this template use File | Settings | File Templates.
 */
public abstract class TableModelDecorator implements TableModel, TableModelListener {
    private static final Logger log = Logger.getLogger(TableModelDecorator.class);

    protected TableModelDecorator(TableModel realTableModel) {
        this.realTableModel = realTableModel;
        this.realTableModel.addTableModelListener(this);
    }

    public TableModel getRealTableModel() {
        return realTableModel;
    }

    TableModel realTableModel;

    public int getColumnCount() {
        return realTableModel.getColumnCount();
    }

    public int getRowCount() {
        return realTableModel.getRowCount();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return realTableModel.isCellEditable(rowIndex, columnIndex);
    }

    public Class getColumnClass(int columnIndex) {
        return realTableModel.getColumnClass(columnIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return realTableModel.getValueAt(rowIndex, columnIndex);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        realTableModel.setValueAt(aValue, rowIndex, columnIndex);
    }

    public String getColumnName(int columnIndex) {
        return realTableModel.getColumnName(columnIndex);
    }

    public void addTableModelListener(TableModelListener l) {
        realTableModel.addTableModelListener(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        realTableModel.removeTableModelListener(l);
    }
}
