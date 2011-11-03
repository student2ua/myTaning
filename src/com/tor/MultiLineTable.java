package com.tor;

/*
Core SWING Advanced Programming
By Kim Topley
ISBN: 0 13 083292 8
Publisher: Prentice Hall
*/


import com.tor.swing.table.cellRenderer.StripedTableCellRenderer;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

public class MultiLineTable {
  public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception evt) {}

    JFrame f = new JFrame("Multi-line Cell Table");
    JTable tbl = new JTable(new MultiLineTableModel());

    // Create the custom cell renderer
    MultiLineCellRenderer multiLineRenderer = new MultiLineCellRenderer(
        SwingConstants.LEFT, SwingConstants.CENTER);

    TableColumnModel tcm = tbl.getColumnModel();
    tcm.getColumn(0).setPreferredWidth(75);
    tcm.getColumn(0).setMinWidth(75);
    tcm.getColumn(1).setPreferredWidth(150);
    tcm.getColumn(1).setMinWidth(150);

    // Install the multi-line renderer
    tcm.getColumn(0).setCellRenderer(multiLineRenderer);
    tcm.getColumn(1).setCellRenderer(multiLineRenderer);

    // Set the table row height
    tbl.setRowHeight(56);

    // Add the stripe renderer.
    StripedTableCellRenderer.installInTable(tbl, Color.lightGray,
        Color.white, null, null);

  //  tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tbl.setPreferredScrollableViewportSize(tbl.getPreferredSize());

    JScrollPane sp = new JScrollPane(tbl);
    f.getContentPane().add(sp, "Center");
    f.pack();
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
    f.setVisible(true);
  }
}

class MultiLineTableModel extends AbstractTableModel {
  protected String[] columnNames = { "Flight", "Crew" };

  // Implementation of TableModel interface
  public int getRowCount() {
    return data.length;
  }

  public int getColumnCount() {
    return columnNames.length;
  }

  public Object getValueAt(int row, int column) {
    return data[row][column];
  }

  public Class getColumnClass(int column) {
    return (data[0][column]).getClass();
  }

  public String getColumnName(int column) {
    return columnNames[column];
  }

  protected Object[][] data = new Object[][] {
      {
          "Apollo 11",
          new String[] { "Neil Armstrong", "Buzz Aldrin",
              "Michael Collins" } },
      {
          "Apollo 12",
          new String[] { "Pete Conrad", "Alan Bean", "Richard Gordon" } },
      {
          "Apollo 13",
          new String[] { "James Lovell", "Fred Haise", "Jack Swigert" } },
      {
          "Apollo 14",
          new String[] { "Alan Shepard", "Edgar Mitchell",
              "Stuart Roosa" } },
      { "Apollo 15",
          new String[] { "Dave Scott", "Jim Irwin", "Al Worden" } },
      {
          "Apollo 16",
          new String[] { "John Young", "Charlie Duke",
              "Ken Mattingly" } },
      {
          "Apollo 17",
          new String[] { "Eugene Cernan", "Harrison Schmitt",
              "Ron Evans" } } };
}

