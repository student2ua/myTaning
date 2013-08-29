package com.tor.swing.tree.kumars;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.08.13
 * Time: 18:50
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener {
    private CheckTreeSelectionModel selectionModel;
    private JTree tree = new JTree();
    int hotspot = new JCheckBox().getPreferredSize().width;

    public CheckTreeManager(JTree tree) {
        this.tree = tree;
        selectionModel = new CheckTreeSelectionModel(tree.getModel());
        tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel));
        tree.addMouseListener(this);
        selectionModel.addTreeSelectionListener(this);
    }

    public void mouseClicked(MouseEvent me) {
        TreePath path = tree.getPathForLocation(me.getX(), me.getY());
        if (path == null)
            return;
        if (me.getX() > tree.getPathBounds(path).x + hotspot)
            return;

        boolean selected = selectionModel.isPathSelected(path, true);
        selectionModel.removeTreeSelectionListener(this);

        try {
            if (selected)
                selectionModel.removeSelectionPath(path);
            else
                selectionModel.addSelectionPath(path);
        } finally {
            selectionModel.addTreeSelectionListener(this);
            tree.treeDidChange();
        }
    }

    public CheckTreeSelectionModel getSelectionModel() {
        return selectionModel;
    }

    public void valueChanged(TreeSelectionEvent e) {
        tree.treeDidChange();
    }
}
