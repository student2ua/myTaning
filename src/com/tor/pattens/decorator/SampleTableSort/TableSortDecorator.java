package com.tor.pattens.decorator.SampleTableSort;

import org.apache.log4j.Logger;

import javax.swing.table.TableModel;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 14:30:28
 * To change this template use File | Settings | File Templates.
 */
public abstract class TableSortDecorator extends TableModelDecorator {
    private static final Logger log = Logger.getLogger(TableSortDecorator.class);

    public abstract void sort(int column);

    protected TableSortDecorator(TableModel realModel) {
        super(realModel);
    }
}
