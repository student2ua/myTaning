package com.tor.pattens.decorator.SampleTableSort;

import com.tor.pattens.decorator.SampleTableSort.filterImpl.TableHighPriceFilter;
import com.tor.pattens.decorator.SampleTableSort.sortImpl.TableBubbleSortDecorator;
import obuchenie.PineTableModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.03.2010
 * Time: 15:37:28
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTableDecoratorMaine extends JFrame {
    private static final Logger log = Logger.getLogger(SimpleTableDecoratorMaine.class);
    JPanel panel = new JPanel();
    private JTable table = new JTable(new PineTableModel());
    private final TableSortDecorator tableSortDecorator = new TableBubbleSortDecorator(table.getModel());
    private final TableFilterDecorator tableFilterDecorator = new TableHighPriceFilter(tableSortDecorator);
    JScrollPane scroll = new JScrollPane(table);
    private JToggleButton filterButton = new JToggleButton("A filter");

    public SimpleTableDecoratorMaine() {

        setTitle("SimpleTableDecorator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        table.setModel((TableModel) tableFilterDecorator);

        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scroll);
        panel.add(filterButton);

        JTableHeader jTableHeader = table.getTableHeader();
        jTableHeader.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("e = " + e);
                if (e.getClickCount() == 2) {
                    int colInTable = table.columnAtPoint(e.getPoint());
                    int colInModel = table.convertColumnIndexToModel(colInTable);
                    tableSortDecorator.sort(colInModel);
                    System.out.println("Sort " + colInTable + " column");
                }
            }
        });
        filterButton.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int colInTable = table.getSelectedColumn();
                int colInModel = table.convertColumnIndexToModel(colInTable);
                System.out.println("Filtrated " + colInTable + " column, ModelCol= " + colInModel);
                if (filterButton.isSelected()) {
                    tableFilterDecorator.filter(colInModel);
                } else {
                    tableSortDecorator.tableChanged(null);
                    tableFilterDecorator.unFilter();
                }
                table.revalidate();
            }
        });
        setSize(800, 600);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleTableDecoratorMaine().setVisible(true);
            }
        });

    }
}
