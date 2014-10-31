package com.tor.swing.table;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 31.10.14
 * Time: 21:42
 * http://tips4java.wordpress.com/2008/11/05/fixed-column-table/
 *
 */
public class FixedColumnTableExemple {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Table FixedColumnTable");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTable table = new JTable(20, 10);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(table);
        FixedColumnTable fct = new FixedColumnTable(2, scrollPane);
        sof(fct);
        JPanel panel = new JPanel();
        panel.add(scrollPane);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    //see  http://stackoverflow.com/questions/2548139/how-can-i-create-a-jtable-where-the-first-column-is-always-in-the-jscrollpane-vi
    private static void sof(FixedColumnTable fixedColumnTable){
        MouseAdapter ma = new MouseAdapter()
        {
            TableColumn column;
            int columnWidth;
            int pressedX;

            public void mousePressed(MouseEvent e)
            {
                JTableHeader header = (JTableHeader)e.getComponent();
                TableColumnModel tcm = header.getColumnModel();
                int columnIndex = tcm.getColumnIndexAtX( e.getX() );
                Cursor cursor = header.getCursor();


                if (columnIndex == tcm.getColumnCount() - 1
                        &&  cursor == Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR))
                {
                    column = tcm.getColumn( columnIndex );
                    columnWidth = column.getWidth();
                    pressedX = e.getX();
                    header.addMouseMotionListener( this );
                }
            }

            public void mouseReleased(MouseEvent e)
            {
                JTableHeader header = (JTableHeader)e.getComponent();
                header.removeMouseMotionListener( this );
            }

            public void mouseDragged(MouseEvent e)
            {
                int width = columnWidth - pressedX + e.getX();
                column.setPreferredWidth( width );
                JTableHeader header = (JTableHeader)e.getComponent();
                JTable table = header.getTable();
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                JScrollPane scrollPane = (JScrollPane)table.getParent().getParent();
                scrollPane.revalidate();
            }
        };

        JTable fixed = fixedColumnTable.getFixedTable();
        fixed.getTableHeader().addMouseListener( ma );
    }
}
