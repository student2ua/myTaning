package com.tor.swing;

import com.tor.swing.table.DnDTable;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.12.2009
 * Time: 17:35:21
 * @see DnDTable
 */
public class DTableDemo extends JFrame{
    private static final Logger log = Logger.getLogger(DTableDemo.class);
    private DnDTable dTable1;
    private DnDTable dTable2;

    public DTableDemo() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        JScrollPane jsp1 = new JScrollPane();
        dTable1 = new DnDTable(new DefaultTableModel(
                new Object[][]{
                    {"a", "a"},
                    {"b", "b"},
                    {"c", "c"},
                    {"d", "d"},
                    {"e", "e"}
                },
                new Object[]{
                    "column 1", "column 2"
                }));
        add(jsp1);
        jsp1.setViewportView(dTable1);
        dTable1.setDropMode(DropMode.ON_OR_INSERT_ROWS);

        JScrollPane jsp2 = new JScrollPane();
        dTable2 = new DTable(new DefaultTableModel(
                new Object[][]{
                    {"A", "A"},
                    {"B", "B"},
                    {"C", "C"},
                    {"D", "D"},
                    {"E", "E"}
                },
                new Object[]{
                    "column 1", "column 2"
                }));
        add(jsp2);
        jsp2.setViewportView(dTable2);
        dTable2.setDropMode(DropMode.ON_OR_INSERT_ROWS);

        pack();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                DTableDemo dtd = new DTableDemo();
                dtd.setVisible(true);
            }
        });
    }
}
