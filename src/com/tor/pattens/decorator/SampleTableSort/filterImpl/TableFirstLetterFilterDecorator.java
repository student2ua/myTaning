package com.tor.pattens.decorator.SampleTableSort.filterImpl;

import com.tor.pattens.decorator.SampleTableSort.TableFilterDecorator;
import org.apache.log4j.Logger;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 17:03:44
 * To change this template use File | Settings | File Templates.
 */
public class TableFirstLetterFilterDecorator extends TableFilterDecorator {
    private static final Logger log = Logger.getLogger(TableFirstLetterFilterDecorator.class);

    ArrayList indexes = new ArrayList();
    HashMap hideded = new HashMap();
    String fistLetter = "1";

    public TableFirstLetterFilterDecorator(TableModel tableModel) {
        super(tableModel);
        allocate();
    }

    public Object getValueAt(int row, int column) {
        System.out.println("row = " + row + ", column = " + column);
        System.out.println("hid = " + hideded.keySet());
        if (hideded.keySet().contains(new Integer(row))) return null;
        return getRealTableModel().getValueAt(((Integer) indexes.get(row)).intValue(), column);
    }

    public void setValueAt(Object aValue, int row, int column) {
        if (hideded.keySet().contains(new Integer(row))) return;
        getRealTableModel().setValueAt(aValue, ((Integer) indexes.get(row)).intValue(), column);
    }

    public void filter(int column) {
        TableModel model = (TableModel) getRealTableModel();
        int rowCount = model.getRowCount();
        System.out.println("indexes = " + indexes);
        if (column > -1 && rowCount == indexes.size()) {
            for (int i = 0; i < rowCount; i++) {
                {
                    Integer number = (Integer) indexes.get(i);
                    Object item = model.getValueAt(number.intValue(), column);
                    if (fistLetter.charAt(0) == (item.toString().charAt(item.toString().length() - 1))) {
                        System.out.println("item = " + item + ", i= " + i);
                        hideded.put(number, item);
                    }
                }
            }
            indexes.removeAll(hideded.keySet());
        }
        System.out.println("indexes.filtred = " + indexes);
    }

    public void unFilter() {

    }

    public void tableChanged(TableModelEvent e) {
        allocate();
    }

    private void allocate() {
        int size = ((TableModel) getRealTableModel()).getRowCount();
        System.out.println("filDec.allocate=" + size);
        indexes = new ArrayList(size);
        for (int i = 0; i < size; ++i) {
            System.out.println("i = " + i);
            indexes.add(new Integer(i));
        }
        hideded.clear();
    }
}
