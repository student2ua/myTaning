package com.tor.swing.table;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 31.03.2010
 * Time: 16:28:07
 * To change this template use File | Settings | File Templates.
 */
public class JTablePackSample {
    private static final Logger log = Logger.getLogger(JTablePackSample.class);

    public static void packColumns(JTable table) {
        for (int i = 0, columnCount = table.getColumnCount(); i < columnCount; i++) {
            TableCellRenderer renderer = table.getDefaultRenderer(
                    table.getModel().getColumnClass(i)
            );

            int maxWidth = 0;

            for (int j = 0, rowCount = table.getRowCount(); j < rowCount; j++) {
                Component component = renderer.getTableCellRendererComponent(
                        table,
                        table.getValueAt(j, i),
                        true,
                        true,
                        j,
                        i
                );

                maxWidth = Math.max(
                        maxWidth, component.getPreferredSize().width
                );
            }

            TableColumn column = table.getColumnModel().getColumn(i);

            column.setMinWidth(
                    maxWidth + table.getIntercellSpacing().width
            );
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
                        "a", "b", "c"
                }
        );

        JTable table = new JTable(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        packColumns(table);

        JScrollPane tableScrollPane = new JScrollPane(table);
        JFrame frame = new JFrame();

        frame.setContentPane(tableScrollPane);
        frame.setSize(512, 384);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
