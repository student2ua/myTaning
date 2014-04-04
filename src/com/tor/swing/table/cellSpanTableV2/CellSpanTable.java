package com.tor.swing.table.cellSpanTableV2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

/**
 * User: tor
 * Date: 25.11.13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class CellSpanTable extends JTable {

    private static final Rectangle ZERO_SIZE_RECTANGLE = new Rectangle(0, 0, 0, 0);


    @Override
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {

        if ((row < 0) || (column < 0) || (getRowCount() <= row) || (getColumnCount() <= column)) {
            return super.getCellRect(row, column, includeSpacing);
        }

        if (!(getModel() instanceof SpanModel)) {
            return super.getCellRect(row, column, includeSpacing);
        }

        final SpanModel model = (SpanModel) getModel();
        if (!model.isCellSpanOn()) {
            return super.getCellRect(row, column, includeSpacing);
        }

        CellSpan cellSpan = model.getCellSpanAt(row, column);

        //если запрашиваемая ячейка перекрыта, то возвращаем 0-вой прямоугольник
        if (cellSpan != null &&
                ((row >= cellSpan.getRow() //&& row
                        && (column >= cellSpan.getColumn() //&& column
                        && !(row == cellSpan.getRow() && column == cellSpan.getColumn()))))) {
            return ZERO_SIZE_RECTANGLE;
        }


        int rowspan = 1, columnspan = 1;

        if (cellSpan != null) {
            columnspan = cellSpan.getColumnSpan();
            rowspan = cellSpan.getRowSpan();
        }


        int index = 0;
        Rectangle cellFrame = new Rectangle();
        //считаем высоту ячейки (все ячейки одинаковы по высоте)
        int aCellHeight = rowHeight + rowMargin;
        cellFrame.y = row * aCellHeight;
        cellFrame.height = rowspan * aCellHeight;

        Enumeration enumeration = getColumnModel().getColumns();
        //считаем позицию левой стороны ячейки
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            cellFrame.width = aColumn.getWidth();
            if (index == column) break;
            cellFrame.x += cellFrame.width;
            index++;
        }
        //считаем ширину ячейки
        for (int i = 0; i < columnspan - 1; i++) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            cellFrame.width += aColumn.getWidth();
        }


        if (!includeSpacing) {
            Dimension spacing = getIntercellSpacing();
            cellFrame.setBounds(cellFrame.x + spacing.width / 2,
                    cellFrame.y + spacing.height / 2,
                    cellFrame.width - spacing.width,
                    cellFrame.height - spacing.height);
        }
        return cellFrame;
    }

    private int[] rowColumnAtPoint(Point point) {
        int[] retValue = {-1, -1};
        int row = point.y / (rowHeight + rowMargin);
        if ((row < 0) || (getRowCount() <= row)) return retValue;
        int column = getColumnModel().getColumnIndexAtX(point.x);

        if (!(getModel() instanceof SpanModel)) {
            return new int[]{column, row};
        }

        final SpanModel model = (SpanModel) getModel();
        if (!model.isCellSpanOn()) {
            return new int[]{column, row};
        }

        CellSpan cellSpan = model.getCellSpanAt(row, column);
        //если запрашиваемая ячейка перекрыта, то возвращаем координаты перекрывающей ячейки
        if (cellSpan != null) {
            retValue[0] = cellSpan.getColumn();
            retValue[1] = cellSpan.getRow();
            return retValue;
        }

        retValue[0] = column;
        retValue[1] = row;
        return retValue;
    }


    public int rowAtPoint(Point point) {
        return rowColumnAtPoint(point)[1];
    }

    public int columnAtPoint(Point point) {
        return rowColumnAtPoint(point)[0];
    }


    public void columnSelectionChanged(ListSelectionEvent e) {
        repaint();
    }

    public void valueChanged(ListSelectionEvent e) {
        int firstIndex = e.getFirstIndex();
        int lastIndex = e.getLastIndex();
        if (firstIndex == -1 && lastIndex == -1) { // Selection cleared.
            repaint();
        }
        Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
        int numCoumns = getColumnCount();
        int index = firstIndex;
        for (int i = 0; i < numCoumns; i++) {
            dirtyRegion.add(getCellRect(index, i, false));
        }
        index = lastIndex;
        for (int i = 0; i < numCoumns; i++) {
            dirtyRegion.add(getCellRect(index, i, false));
        }
        repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
    }

    @Override
    public void updateUI() {
        if (UIManager.get("BasicCellSpanTableUI") == null) {
            UIManager.put("BasicCellSpanTableUI", "grid.BasicCellSpanTableUI");
            UIManager.put("grid.BasicCellSpanTableUI", BasicCellSpanTableUI.class);
        }
        final ComponentUI newUI = UIManager.getUI(this);
        setUI(newUI);
    }

    public String getUIClassID() {
        return "BasicCellSpanTableUI";
    }

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("demo");
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                CellSpanTable table = new CellSpanTable();
                table.setModel(new SpanTableModel());           //собственно вся куйня в моделе
                frame.getContentPane().add(new JScrollPane(table));
                frame.setSize(400, 200);
                frame.setVisible(true);
            }
        });
    }
}