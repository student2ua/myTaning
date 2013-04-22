package com.tor.swing.table.rowNum.n3;
/*
Java Swing, 2nd Edition
By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
ISBN: 0-596-00408-7
Publisher: O'Reilly
*/
// RowHeaderTable.java
//A simple application that demonstrates the use of the TableColumnModel
//class to build a row header column that scrolls with the regular data
//rows.
//

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class RowHeaderTable extends JFrame {

    public RowHeaderTable() {
        super("Row Header Test");
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        TableModel tm = new AbstractTableModel() {
            String data[] = {"", "a", "b", "c", "d", "e"};

            String headers[] = {"Row #", "Column 1", "Column 2", "Column 3", "Column 4", "Column 5"};

            public int getColumnCount() {
                return data.length;
            }

            public int getRowCount() {
                return 1000;
            }

            public String getColumnName(int col) {
                return headers[col];
            }

            // Synthesize some entries using the data values & the row #
            public Object getValueAt(int row, int col) {
                return data[col] + row;
            }
        };

        // Create a column model for the main table. This model ignores the
        // first
        // column added, and sets a minimum width of 150 pixels for all others.
        TableColumnModel cm = new DefaultTableColumnModel() {
            boolean first = true;

            public void addColumn(TableColumn tc) {
                // Drop the first column . . . that'll be the row header
                if (first) {
                    first = false;
                    return;
                }
                tc.setMinWidth(150); // just for looks, really...
                super.addColumn(tc);
            }
        };

        // Create a column model that will serve as our row header table. This
        // model picks a maximum width and only stores the first column.
        TableColumnModel rowHeaderModel = new DefaultTableColumnModel() {
            boolean first = true;

            public void addColumn(TableColumn tc) {
                if (first) {
                    tc.setMaxWidth(tc.getPreferredWidth());
                    super.addColumn(tc);
                    first = false;
                }
                // Drop the rest of the columns . . . this is the header column
                // only
            }
        };

        JTable jt = new JTable(tm, cm);

        // Set up the header column and get it hooked up to everything
        JTable headerColumnTable = new JTable(tm, rowHeaderModel);
        jt.createDefaultColumnsFromModel();
        headerColumnTable.createDefaultColumnsFromModel();

        // Make sure that selections between the main table and the header stay
        // in sync (by sharing the same model)
        jt.setSelectionModel(headerColumnTable.getSelectionModel());

        // Make the header column look pretty
        //headerColumnTable.setBorder(BorderFactory.createEtchedBorder());
        headerColumnTable.setBackground(Color.lightGray);
        headerColumnTable.setColumnSelectionAllowed(false);
        headerColumnTable.setCellSelectionEnabled(false);

        // Put it in a viewport that we can control a bit
        JViewport jv = new JViewport();
        jv.setView(headerColumnTable);
        jv.setPreferredSize(headerColumnTable.getMaximumSize());

        // With out shutting off autoResizeMode, our tables won't scroll
        // correctly (horizontally, anyway)
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // We have to manually attach the row headers, but after that, the
        // scroll
        // pane keeps them in sync
        JScrollPane jsp = new JScrollPane(jt);
        jsp.setRowHeader(jv);
        jsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, headerColumnTable
                .getTableHeader());
        getContentPane().add(jsp, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
        RowHeaderTable rht = new RowHeaderTable();
        rht.setVisible(true);
    }
}