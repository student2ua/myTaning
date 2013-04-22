package com.tor.swing.table.rowNum.n1;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 31.05.11
 * Time: 13:37
 * Use a JTable as a renderer for row numbers of a given main table.
 * This table must be added to the row header of the scrollpane that
 * contains the main table.
 */
public class RowNumberTable extends JTable
        implements ChangeListener, PropertyChangeListener {

    private final JTable table;

    public RowNumberTable(JTable table) {
        this.table = table;
        this.table.addPropertyChangeListener(this);

        setFocusable(false);
        setAutoCreateColumnsFromModel(false);

        updateRowHeight();
        updateModel();
        updateSelectionModel();

        TableColumn column = new TableColumn();
        column.setHeaderValue("");
        addColumn(column);
        column.setCellRenderer(new RowNumberRenderer());

        getColumnModel().getColumn(0).setPreferredWidth(50);
        setPreferredScrollableViewportSize(getPreferredSize());

        getTableHeader().setReorderingAllowed(false);
    }

    public void addNotify() {
        super.addNotify();

        Component c = getParent();

        // Keep scrolling of the row table in sync with the main table.

        if (c instanceof JViewport) {
            JViewport viewport = (JViewport) c;
            viewport.addChangeListener(this);
        }
    }

    /*
    * Delegate method to main table
    */
    public int getRowCount() {
        return table != null ? table.getRowCount() : 0;
    }

    public int getRowHeight(int row) {
        return table.getRowHeight(row);
    }

    /*
    * This table does not use any data from the main TableModel,
    * so just return a value based on the row parameter.
    */
    public Object getValueAt(int row, int column) {
        return Integer.toString(row + 1);
    }

    /*
    * Don't edit data in the main TableModel by mistake
    */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    // implements ChangeListener
    public void stateChanged(ChangeEvent e) {
        // Keep the scrolling of the row table in sync with main table

        JViewport viewport = (JViewport) e.getSource();
        JScrollPane scrollPane = (JScrollPane) viewport.getParent();
        scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
    }

    // implements PropertyChangeListener
    public void propertyChange(PropertyChangeEvent e) {
        // Keep the row table in sync with the main table

        if ("rowHeight".equals(e.getPropertyName())) {
            updateRowHeight();
        }

        if ("selectionModel".equals(e.getPropertyName())) {
            updateSelectionModel();
        }

        if ("model".equals(e.getPropertyName())) {
            updateModel();
        }
    }

    private void updateRowHeight() {
        setRowHeight(table.getRowHeight());
    }

    private void updateModel() {
        setModel(table.getModel());
    }

    private void updateSelectionModel() {
        setSelectionModel(table.getSelectionModel());
    }

    /*
    * Borrow the renderer from JDK1.4.2 table header
    */
    private static class RowNumberRenderer extends DefaultTableCellRenderer {

        public RowNumberRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (table != null) {
                JTableHeader header = table.getTableHeader();

                if (header != null) {
                    setForeground(header.getForeground());
                    setBackground(header.getBackground());
                    setFont(header.getFont());
                }
            }

            if (isSelected) {
                setFont(getFont().deriveFont(Font.BOLD));
            }

            setText((value == null) ? "" : value.toString());
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));

            return this;
        }
    }

    public static void main(String[] args) {
        TableModel tableModel = new DefaultTableModel(
                new String[][]{
                        {"abc", "abcdefg", "abcdefghijk"
                        }, {"abcdefghijklmno", "absdefghi", "abc"
                }, {"abcde", "abcdefghijklmnopqrstuvwxyz", "abcdefgjijklmn"
                },
                },
                new String[]{
                        "a1", "b2", "c3"
                }
        );

        JTable mainTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(mainTable);
        JTable rowTable = new RowNumberTable(mainTable);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
        JFrame frame = new JFrame();

        frame.setContentPane(scrollPane);
        frame.setSize(512, 384);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}