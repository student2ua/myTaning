package com.tor.swing.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 30.01.13
 * Time: 18:07
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class JHorizontalFriendlyTable extends JTable {

    @Override
    public Dimension getPreferredSize() {
        if (getParent() instanceof JViewport) {
            if (((JViewport) getParent()).getWidth() > super.getPreferredSize().width) {
                return getMinimumSize();
            }
        }
        return super.getPreferredSize();
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        if (autoResizeMode != AUTO_RESIZE_OFF) {
            if (getParent() instanceof JViewport) {
                return (((JViewport) getParent()).getWidth() > getPreferredSize().width);
            }
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        JScrollPane jScrollPane1 = new JScrollPane();
        TableModel jTable1Model = new DefaultTableModel(new String[][]{
                {"abc", "abcdefg", "abcdefghijk"},
                {"abcdefghijklmno", "absdefghi", "abc"},
                {"abcde", "abcdefghijklmnopqrstuvwxyz", "abcdefgjijklmn"},
        },
                new String[]{
                        "a1", "b2", "c3"
                });
        JTable jTable1 = new JHorizontalFriendlyTable();
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setViewportView(jTable1);
        jTable1.setModel(jTable1Model);
        jTable1.setPreferredSize(new java.awt.Dimension(1051, 518));
        jTable1.setPreferredScrollableViewportSize(new java.awt.Dimension(1000, 528));
        jTable1.getSize(new java.awt.Dimension(1051, 528));

        if (jTable1.getPreferredScrollableViewportSize().getWidth() >
                ((JViewport) jTable1.getParent()).getPreferredSize().getWidth()) {
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jTable1.doLayout();
        }

        jTable1.setDragEnabled(false);
        jTable1.setColumnSelectionAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        JFrame jFrame = new JFrame();
        jFrame.getContentPane().add(jScrollPane1);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
    /** or only
     table.getParent().addComponentListener(new ComponentAdapter() {
    @Override public void componentResized(final ComponentEvent e) {
    if (table.getPreferredSize().width < table.getParent().getWidth()) {
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    } else {
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    }
    });
     @see com.ecwo.project2002.presentation.TypeControlledMarkResultFrame#createCenterComponent()*/
}
