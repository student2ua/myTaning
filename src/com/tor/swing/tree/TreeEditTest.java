package com.tor.swing.tree;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: tor
 * Date: 23.12.2008
 * Time: 15:53:36
 */
public class TreeEditTest {

    public static void main(String[] args) {
        JFrame frame = new TreeEditFrame();
        frame.setVisible(true);
    }
}

class TreeEditFrame extends JFrame {

    private DefaultTreeModel model;
    private JTree tree;
    private JButton addSiblingButton;
    private JButton addChildButton;
    private JButton deleteButton;
    private JButton editButton;

    public TreeEditFrame() {
        setTitle("TreeEditTest");
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // construct tree

        TreeNode root = makeSampleTree();
        model = new DefaultTreeModel(root);
        tree = new JTree(model);
        tree.setEditable(true);


        // add scroll pane with tree to content pane

        Container contentPane = getContentPane();
        contentPane.add(new JScrollPane(tree), BorderLayout.CENTER);

        // make button panel
        contentPane.add(makeButtonPanel(), BorderLayout.SOUTH);

        tree.setComponentPopupMenu(makePopupMenu());
    }

    private JPopupMenu makePopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu("Title");
        popupMenu.add(addChildButton.getAction());
        popupMenu.add(addSiblingButton.getAction());
        popupMenu.add(deleteButton.getAction());
        return popupMenu;
    }

    private JPanel makeButtonPanel() {
        JPanel panel = new JPanel();
        addSiblingButton = new JButton(new AddSiblingAction());
        panel.add(addSiblingButton);
        addChildButton = new JButton(new AddChildAction());
        panel.add(addChildButton);
        deleteButton = new JButton(new DeleteNodeAction());
        panel.add(deleteButton);
        return panel;
    }

    public TreeNode makeSampleTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("World");
        DefaultMutableTreeNode country = new DefaultMutableTreeNode("USA");
        root.add(country);
        DefaultMutableTreeNode state = new DefaultMutableTreeNode("California");
        country.add(state);
        DefaultMutableTreeNode city = new DefaultMutableTreeNode("San Jose");
        state.add(city);
        city = new DefaultMutableTreeNode("Cupertino");
        state.add(city);
        state = new DefaultMutableTreeNode("Michigan");
        country.add(state);
        city = new DefaultMutableTreeNode("Ann Arbor");
        state.add(city);
        country = new DefaultMutableTreeNode("Germany");
        root.add(country);
        state = new DefaultMutableTreeNode("Schleswig-Holstein");
        country.add(state);
        city = new DefaultMutableTreeNode("Kiel");
        state.add(city);
        return root;
    }

    private class AddChildAction extends AbstractAction {
        private AddChildAction() {
            putValue(Action.NAME, "Add Child");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null) return;

            MutableTreeNode newNode = new DefaultMutableTreeNode("New");
            model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());

            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            tree.scrollPathToVisible(path);
        }
    }

    private class AddSiblingAction extends AbstractAction {
        private AddSiblingAction() {
            putValue(Action.NAME, "Add Sibling");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null) return;
            MutableTreeNode newNode = new DefaultMutableTreeNode("New");

            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();

            if (parent != null) {
                int selectedIndex = parent.getIndex(selectedNode);
                model.insertNodeInto(newNode, parent, selectedIndex + 1);
            }
            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            tree.scrollPathToVisible(path);

        }
    }

    private class DeleteNodeAction extends AbstractAction {
        private DeleteNodeAction() {
            putValue(Action.NAME, "Delete");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null)
                return;


            if (selectedNode.getParent() != null)
                model.removeNodeFromParent(selectedNode);


        }
    }
}

