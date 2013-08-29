package com.tor.swing.tree.kumars;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.08.13
 * Time: 19:32
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import javax.swing.tree.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CheckTreeTest {
    public static void main(String args[]) {


       /* TreeNode root = getExampleRootNode();
        DefaultTreeModel model = new DefaultTreeModel(root);
        JTree srvTree = new JTree(model);*/
        JTree srvTree = new JTree();
      /*  DefaultTreeCellRenderer myRenderer = new DefaultTreeCellRenderer();

        srvTree.setCellRenderer(myRenderer);
      */
        CheckTreeManager checkTreeManager = new CheckTreeManager(srvTree);
        TreeSelectionModel selModel = checkTreeManager.getSelectionModel();

        //  selModel.addSelectionPaths(getExampleSelectedPath(model));
        for (int i = 0; i < srvTree.getRowCount(); i++) {
            srvTree.expandRow(i);
        }
        JScrollPane TreePanel = new JScrollPane(srvTree);
        JFrame KK = new JFrame("Demo");
        KK.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        KK.add(TreePanel);
        KK.setBounds(200, 200, 200, 200);
        KK.setVisible(true);

    }

    /*  public void setSelectionPaths(TreePath[] pPaths){
      clearSelection();
      for(int i=0; i<pPaths.length; i++)
      addSelectionPath(pPaths[i]);
      }*/
    public void addChildPaths(TreePath path, TreeModel model, List result) {
        Object item = path.getLastPathComponent();
        int childCount = model.getChildCount(item);
        for (int i = 0; i < childCount; i++)
            result.add(path.pathByAddingChild(model.getChild(item, i)));
    }

    public ArrayList getDescendants(TreePath paths[], TreeModel model) {
        ArrayList result = new ArrayList();
        Stack pending = new Stack();
        pending.addAll(Arrays.asList(paths));
        while (!pending.isEmpty()) {
            TreePath path = (TreePath) pending.pop();
            addChildPaths(path, model, pending);
            result.add(path);
        }
        return result;
    }


    public ArrayList getAllCheckedPaths(CheckTreeManager manager, JTree tree) {
        return getDescendants(manager.getSelectionModel().getSelectionPaths(), tree.getModel());
    }

    private static TreePath[] getExampleSelectedPath(DefaultTreeModel model) {
        TreeNode[] nodes = model.getPathToRoot(new DefaultMutableTreeNode("city1"));
        TreePath hh1 = new TreePath(nodes);
        nodes = model.getPathToRoot(new DefaultMutableTreeNode("city2"));
        TreePath hh2 = new TreePath(nodes);
        nodes = model.getPathToRoot(new DefaultMutableTreeNode("city3"));
        TreePath hh3 = new TreePath(nodes);
        return new TreePath[]{hh1, hh2, hh3};
    }

    public static TreeNode getExampleRootNode() {
        DefaultMutableTreeNode world = new DefaultMutableTreeNode("World");

        DefaultMutableTreeNode country1 = new DefaultMutableTreeNode("country1");
        DefaultMutableTreeNode city1 = new DefaultMutableTreeNode("city1");
        country1.add(city1);
        world.add(country1);

        DefaultMutableTreeNode country2 = new DefaultMutableTreeNode("country2");
        DefaultMutableTreeNode city2 = new DefaultMutableTreeNode("city2");
        country2.add(city2);
        world.add(country2);

        DefaultMutableTreeNode country3 = new DefaultMutableTreeNode("country3");
        DefaultMutableTreeNode city3 = new DefaultMutableTreeNode("city3");
        country3.add(city3);
        world.add(country3);

        DefaultMutableTreeNode country4 = new DefaultMutableTreeNode("country4");
        DefaultMutableTreeNode city4 = new DefaultMutableTreeNode("city4");
        country4.add(city4);
        world.add(country4);

        return world;
    }
}