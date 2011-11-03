package com.tor.pattens.decorator.SampleTableSort.sortImpl;

import com.tor.pattens.decorator.SampleTableSort.TableSortDecorator;
import org.apache.log4j.Logger;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import java.text.Collator;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 16:19:33
 * To change this template use File | Settings | File Templates.
 */
public class TableBubbleSortDecorator extends TableSortDecorator {
    private static final Logger log = Logger.getLogger(TableBubbleSortDecorator.class);
    int indexes[];
    Collator collator = Collator.getInstance(new Locale("ru", "RU"));

    public void setCollator(Collator vCollator) {
        this.collator = vCollator;
    }

    public TableBubbleSortDecorator(TableModel realModel) {
        super(realModel);
        allocate();
    }

    public void tableChanged(TableModelEvent e) {
        allocate();
    }

    public Object getValueAt(int row, int column) {
        return getRealTableModel().getValueAt(indexes[row], column);
    }

    public void setValueAt(Object aValue, int row, int column) {
        getRealTableModel().setValueAt(aValue, indexes[row], column);
    }

    // The following methods perform the bubble sort ...
    public void sort(int column) {
        TableModel model = (TableModel) getRealTableModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = i + 1; j < rowCount; j++) {
                if (compare(indexes[i], indexes[j], column) < 0) {
                    swap(i, j);
                }
            }
        }
    }

    private void swap(int i, int j) {
        int tmp = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = tmp;
    }

    private int compare(int i, int j, int column) {
        TableModel realModel = (TableModel) getRealTableModel();
        Object io = realModel.getValueAt(i, column);
        Object jo = realModel.getValueAt(j, column);

        int c = collator.compare(jo.toString(), io.toString());
        //old  jo.toString().compareTo(io.toString());
        return (c < 0) ? -1 : ((c > 0) ? 1 : 0);
    }

    private void allocate() {
        indexes = new int[((TableModel) getRealTableModel()).getRowCount()];
        System.out.println("TableBubbleSortDecorator.allocate");
        System.out.println("TableModel.getRowCount() = " + ((TableModel) getRealTableModel()).getRowCount());
        System.out.print(" [");
        for (int i = 0; i < indexes.length; ++i) {
            indexes[i] = i;
            System.out.print(indexes[i] + ", ");
        }
        System.out.print("]");
    }
}
