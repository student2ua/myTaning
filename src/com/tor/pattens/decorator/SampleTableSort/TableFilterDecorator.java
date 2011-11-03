package com.tor.pattens.decorator.SampleTableSort;

import org.apache.log4j.Logger;

import javax.swing.table.TableModel;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 16:51:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class TableFilterDecorator extends TableModelDecorator {
    private static final Logger log = Logger.getLogger(TableFilterDecorator.class);

    public abstract void filter(int column);
    public abstract void unFilter();

    protected TableFilterDecorator(TableModel tableModel) {
        super(tableModel);
    }
}
