package com.tor.swing.table;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.12.2009
 * Time: 17:46:39
 * @version jdk>1.5
 */
public class DnDTable extends JTable {
    private static final Logger log = Logger.getLogger(DnDTable.class);
    public static DataFlavor DTable_Flavor = new DataFlavor(DTableData.class, "DTableData");
    private static DataFlavor[] supportedFlavors = {DTable_Flavor};

    public DnDTable() {
        super();
        setTransferHandler(new ReorderHandler());
        setDragEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }

    public DnDTable(DefaultTableModel m) {
        this();
        setModel(m);
    }

    private class ReorderHandler extends TransferHandler {

     //   @Override
       // @SuppressWarnings({"unchecked", "unchecked"})
        public boolean importData(TransferSupport support) {
            int dropIndex = getDropLocation().getRow();
            int insertionAdjustment = 0;
            try {
                Vector[] draggedData = ((DTableData) support.getTransferable().getTransferData(DTable_Flavor)).data;
                final DnDTable dragTable = ((DTableData) support.getTransferable().getTransferData(DTable_Flavor)).parent;
                DefaultTableModel dragModel = (DefaultTableModel) dragTable.getModel();
                DefaultTableModel dropModel = (DefaultTableModel) DnDTable.this.getModel();

                if (dropIndex == dropModel.getDataVector().size()) {
                    dropIndex--;
                    insertionAdjustment++;
                }

                final Object leadItem = dropIndex >= 0 ? dropModel.getDataVector().get(dropIndex) : null;
                final int dataLength = draggedData.length;

                if (leadItem != null) {
                    for (int i = 0; i < dataLength; i++) {
                        if (draggedData[i].equals(leadItem)) {
                            return false;
                        }
                    }
                }

                final boolean localDrop = dropModel.getDataVector().contains(draggedData[0]);

                for (int i = 0; i < dataLength; i++) {
                    int index = dragModel.getDataVector().indexOf(draggedData[i]);
                    dragModel.removeRow(index);
                }

                if (localDrop) {
                    final int adjustedLeadIndex = dropModel.getDataVector().indexOf(leadItem);

                    for (int i = 0; i < dataLength; i++) {
                        dropModel.insertRow(adjustedLeadIndex + insertionAdjustment + i, draggedData[i]);
                    }

                    SwingUtilities.invokeLater(new Runnable() {

                    //    @Override
                        public void run() {
                            DnDTable.this.clearSelection();
                        }
                    });
                } else {
                    for (int i = 0; i < dataLength; i++) {
                        dropModel.insertRow(dropIndex + insertionAdjustment + i, draggedData[i]);
                    }

                    SwingUtilities.invokeLater(new Runnable() {

                      //  @Override
                        public void run() {
                            DnDTable.this.clearSelection();
                            dragTable.clearSelection();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

      //  @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.MOVE;
        }

      //  @Override
        protected Transferable createTransferable(JComponent c) {
            int[] rows = DnDTable.this.getSelectedRows();
            Vector[] data = new Vector[rows.length];
            for (int j = 0; j < rows.length; j++) {
                data[j] = new Vector();
                for (int i = 0; i < DnDTable.this.getColumnCount(); i++) {
                    data[j].add(DnDTable.this.getModel().getValueAt(rows[j], i));
                }
            }
            return new DTableData(DnDTable.this, data);
        }

       // @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDrop() || !support.isDataFlavorSupported(DTable_Flavor)) {
                return false;
            }
            return true;
        }

     //   @Override
        public Icon getVisualRepresentation(Transferable t) {
            return super.getVisualRepresentation(t);
        }
    }

    private class DTableData implements Transferable {

        public Vector[] data;
        public DnDTable parent;

        protected DTableData(DnDTable p, Vector[] d) {
            parent = p;
            data = d;
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DTable_Flavor)) {
                return DTableData.this;
            } else {
                return null;
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            return supportedFlavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return true;
        }
    }

}
