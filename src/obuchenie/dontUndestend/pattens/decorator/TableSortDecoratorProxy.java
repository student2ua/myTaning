package obuchenie.dontUndestend.pattens.decorator;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.10.2009
 * Time: 18:16:18
 * To change this template use File | Settings | File Templates.
 */
import obuchenie.dontUndestend.pattens.decorator.Decorator;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;
public abstract class TableSortDecoratorProxy
                                 extends Decorator
                                 implements TableModelListener {
   // Concrete extensions of this class must implement
   // tableChanged from TableModelListener
   abstract public void sort(int column);
   public TableSortDecoratorProxy(TableModel realModel) {
      super(realModel);
   }
}

