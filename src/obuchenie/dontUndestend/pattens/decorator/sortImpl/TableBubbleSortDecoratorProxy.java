package obuchenie.dontUndestend.pattens.decorator.sortImpl;

import obuchenie.dontUndestend.pattens.decorator.TableSortDecoratorProxy;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import java.lang.reflect.Method;

public class TableBubbleSortDecoratorProxy extends TableSortDecoratorProxy {
    private int indexes[];
    private static String GET_VALUE_AT = "getValueAt";
    private static String SET_VALUE_AT = "setValueAt";

    public TableBubbleSortDecoratorProxy(TableModel model) {
        super(model);
        allocate();
    }

    // tableChanged is defined in TableModelListener, which
    // is implemented by TableSortDecorator.
    public void tableChanged(TableModelEvent e) {
        allocate();
    }

    // invoke() is defined by the java.lang.reflect.InvocationHandler
    // interface; that interface is implemented by the
    // (abstract) Decorator class. Decorator is the superclass
    // of TableSortDecorator.
    public Object invoke(Object proxy, Method method,
                         Object[] args) {
        Object result = null;
        TableModel model = (TableModel) getDecorated();
        if (GET_VALUE_AT.equals(method.getName())) {
            Integer row = (Integer) args[0],
                    col = (Integer) args[1];
            result = model.getValueAt(indexes[row.intValue()],
                    col.intValue());
        } else if (SET_VALUE_AT.equals(method.getName())) {
            Integer row = (Integer) args[1],
                    col = (Integer) args[2];
            model.setValueAt(args[0], indexes[row.intValue()],
                    col.intValue());
        } else {
            try {
                result = method.invoke(model, args);
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        return result;
    }

    // The following methods perform the bubble sort ...
    public void sort(int column) {
        TableModel model = (TableModel) getDecorated();
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
        TableModel realModel = (TableModel) getDecorated();
        Object io = realModel.getValueAt(i, column);
        Object jo = realModel.getValueAt(j, column);
        int c = jo.toString().compareTo(io.toString());
        return (c < 0) ? -1 : ((c > 0) ? 1 : 0);
    }

    private void allocate() {
        indexes = new int[((TableModel) getDecorated()).
                getRowCount()];
        for (int i = 0; i < indexes.length; ++i) {
            indexes[i] = i;
        }
    }
}

