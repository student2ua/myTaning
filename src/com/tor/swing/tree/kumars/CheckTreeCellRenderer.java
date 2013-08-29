package com.tor.swing.tree.kumars;

import com.tor.swing.tree.kumars.kabutz.TristateCheckBox;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.08.13
 * Time: 18:48
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.jroller.com/santhosh/entry/jtree_with_checkboxes
 */
public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer {
    private CheckTreeSelectionModel selectionModel;
    private TreeCellRenderer delegate;
    private TristateCheckBox checkBox = new TristateCheckBox("");

    public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel) {
        this.delegate = delegate;
        this.selectionModel = selectionModel;
        setLayout(new BorderLayout());
        setOpaque(false);
        checkBox.setOpaque(false);
    }


    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        TreePath path = tree.getPathForRow(row);
        if (path != null) {
            if (selectionModel.isPathSelected(path, true))
                checkBox.setSelected(Boolean.TRUE);
            else if (selectionModel.isPartiallySelected(path)) {
                checkBox.getTristateModel().setIndeterminate();
            } else checkBox.setSelected(Boolean.FALSE);
        }
        removeAll();
        add(checkBox, BorderLayout.WEST);
        add(renderer, BorderLayout.CENTER);
        return this;
    }
}
