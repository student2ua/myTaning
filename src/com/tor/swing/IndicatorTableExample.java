package com.tor.swing;

import com.tor.swing.table.cellRenderer.IndicatorCellRenderer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

//import jp.gr.java_conf.tame.swing.table.*;
/**
 * @version 1.0 03/03/99
 */
public class IndicatorTableExample extends JPanel {
    private static final int MAX = 100;
    private static final int MIN = 0;

    public IndicatorTableExample() {
        setLayout(new BorderLayout());
        DefaultTableModel dm = new DefaultTableModel() {
            public Class getColumnClass(int col) {
                switch (col) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return Object.class;
                }
            }

            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 2:
                        return false;
                    default:
                        return true;
                }
            }

            public void setValueAt(Object obj, int row, int col) {
                if (col != 1) {
                    super.setValueAt(obj, row, col);
                    return;
                }
                try {
                    Integer integer = new Integer(obj.toString());
                    super.setValueAt(checkMinMax(integer), row, col);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        };
        dm.setDataVector(new Object[][]{
                {"not human", new Integer(100), new Integer(100)},
                {"hard worker", new Integer(76), new Integer(76)},
                {"ordinary guy", new Integer(51), new Integer(51)},
                {"lazy fellow", new Integer(12), new Integer(12)}},
                new Object[]{"Name", "Result", "Indicator"});
        JTable table = new JTable(dm);
        IndicatorCellRenderer renderer = new IndicatorCellRenderer(MIN, MAX);
        renderer.setStringPainted(true);
        renderer.setBackground(table.getBackground());
// set limit value and fill color
        Hashtable limitColors = new Hashtable();
        limitColors.put(new Integer(0), Color.green);
        limitColors.put(new Integer(60), Color.yellow);
        limitColors.put(new Integer(80), Color.red);
        renderer.setLimits(limitColors);
        table.getColumnModel().getColumn(2).setCellRenderer(renderer);
        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int col = e.getColumn();
                    if (col == 1) {
                        int row = e.getFirstRow();
                        TableModel model = (TableModel) e.getSource();
                        Integer value = (Integer) model.getValueAt(row, col);
                        model.setValueAt(checkMinMax(value), row, ++col);
                    }
                }
            }
        });
        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("IndicatorTable Example");
        f.getContentPane().add(new IndicatorTableExample(), BorderLayout.CENTER);
        f.setSize(400, 120);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private Integer checkMinMax(Integer value) {
        int intValue = value.intValue();
        if (intValue < MIN) {
            intValue = MIN;
        } else if (MAX < intValue) {
            intValue = MAX;
        }
        return new Integer(intValue);
    }
}

