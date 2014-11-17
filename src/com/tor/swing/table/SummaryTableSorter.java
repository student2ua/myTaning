package com.tor.swing.table;

/**
 * User: tor
 * Date: 12.05.14
 * Time: 14:30
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.Collator;

/**
 * MySwing: Advanced Swing Utilites
 * Copyright (C) 2005  Santhosh Kumar T
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */
public class SummaryTableSorter<M extends TableModel> extends TableRowSorter<M> {
    public SummaryTableSorter(M model) {
        super(model);
    }

    // overridden to use SummaryModelWrapper
    public void setModel(M model) {
        super.setModel(model);
        setModelWrapper(new SummaryModelWrapper<M>(getModelWrapper()));
    }

    // overridden to use TableCellValueComparator always
    protected boolean useToString(int column) {
        return false;
    }

    // overridden to returns TableCellValueComparator
    public java.util.Comparator<?> getComparator(int column) {
        java.util.Comparator comparator = super.getComparator(column);
        if (comparator instanceof Collator)
            comparator = null;
        return new TableCellValueComparator(comparator);
    }

    class SummaryModelWrapper<M extends TableModel> extends DefaultRowSorter.ModelWrapper<M, Integer> {
        private DefaultRowSorter.ModelWrapper<M, Integer> delegate;

        public SummaryModelWrapper(DefaultRowSorter.ModelWrapper<M, Integer> delegate) {
            this.delegate = delegate;
        }

        public M getModel() {
            return delegate.getModel();
        }

        public int getColumnCount() {
            return delegate.getColumnCount();
        }

        public int getRowCount() {
            return delegate.getRowCount();
        }

        public String getStringValueAt(int row, int column) {
            return delegate.getStringValueAt(row, column);
        }

        // returns TableCellValue instances always
        public Object getValueAt(int row, int column) {
            return new TableCellValue(row, column, delegate.getValueAt(row, column));
        }

        public Integer getIdentifier(int row) {
            return delegate.getIdentifier(row);
        }
    }

    class TableCellValue {
        public int row;
        public int column;
        public Object value;

        public TableCellValue(int row, int column, Object value) {
            this.row = row;
            this.column = column;
            this.value = value;
        }
    }

    class TableCellValueComparator implements java.util.Comparator<TableCellValue> {
        private java.util.Comparator delegate;

        public TableCellValueComparator(java.util.Comparator delegate) {
            this.delegate = delegate;
        }

        @SuppressWarnings("unchecked")
        public int compare(TableCellValue cell1, TableCellValue cell2) {
            SortOrder order = SortOrder.ASCENDING;
            for (SortKey sortKey : getSortKeys()) {
                if (sortKey.getColumn() == cell1.column) {
                    order = sortKey.getSortOrder();
                    break;
                }
            }
            int modifier = order == SortOrder.ASCENDING ? 1 : -1;
            if (cell1.row == getModelWrapper().getRowCount() - 1)
                return modifier;
            else if (cell2.row == getModelWrapper().getRowCount() - 1)
                return -modifier;
            else {
                if (delegate != null)
                    return delegate.compare(cell1.value, cell2.value);
                else {
                    String v1 = getModelWrapper().getStringValueAt(cell1.row, cell1.column);
                    String v2 = getModelWrapper().getStringValueAt(cell2.row, cell2.column);
                    if (v1 == null)
                        return v2 == null ? 0 : -1;
                    else
                        return v2 == null ? 1 : v1.compareTo(v2);
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer data[][] = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {15, 18, 21, 24},
        };
        JTable table = new JTable(data, new String[]{"col1", "col2", "col3", "col4"});
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == table.getRowCount() - 1)
                    comp.setBackground(Color.YELLOW);
                else
                    comp.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                return comp;
            }
        });
        table.setRowSorter(new SummaryTableSorter<TableModel>(table.getModel()));
        JFrame frame = new JFrame("Sorting Table with Summary Row - santhosh.tekuri@gmail.com");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(table));
        frame.setSize(250, 250);
        frame.setVisible(true);
    }
}
