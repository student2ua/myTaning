package com.tor.swing.treetable.jjar;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarTable extends JTable {

    final String[] header = {"Entry", "Size", "Date", "Attributes"};

    public JarTable(JarFile jarFile) {
        this.jarFile = jarFile;
        dateFormat = DateFormat.getDateTimeInstance();
        initData();
        JarTableModel model = new JarTableModel();
        setModel(model);
        setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);
        // setIntercellSpacing( new Dimension( 10, 2 ) );
        setShowGrid(false);

        // Set the column widths
        getColumnModel().getColumn(0).setPreferredWidth(400);
        getColumnModel().getColumn(1).setPreferredWidth(70);
        getColumnModel().getColumn(2).setPreferredWidth(200);
        getColumnModel().getColumn(3).setPreferredWidth(300);
    }

    public void setJarFile(JarFile jarFile) {
        this.jarFile = jarFile;
        initData();
        revalidate();
    }

    private void initData() {
        if (jarFile == null)
            return;

        int size = jarFile.size();
        Enumeration jarEntries = jarFile.entries();
        entries = new JarEntry[size];

        for (int i = 0; i < size; i++)
            entries[i] = (JarEntry) jarEntries.nextElement();
    }

    private JarFile jarFile = null;
    private JarEntry[] entries;
    private DateFormat dateFormat;

    class JarTableModel extends AbstractTableModel {
        public int getColumnCount() {
            return header.length;
        }

        public int getRowCount() {
            if (jarFile == null)
                return 0;

            return entries.length;
        }

        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0:
                    return entries[row].getName();
                case 1:
                    return new Long(entries[row].getSize());
                case 2:
                    return dateFormat.format(new Date(entries[row].getTime()));
                case 3:
                    try {
                        Attributes attr = entries[row].getAttributes();
                        if (attr != null)
                            return attr.entrySet();
                    }
                    catch (Exception e) {
                    }
                    return "";
            }
            return "???";
        }

        public String getColumnName(int column) {
            return header[column];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

    ;
}
