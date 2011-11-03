package com.tor.swing.tree;


import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.10.2008
 * Time: 13:57:01
 * SortTreeModel.java
*This class is similar to the DefaultTreeModel, but it keeps
*a node's children in alphabetical order.
*
 */
class SortTreeModel extends DefaultTreeModel {
  private Comparator comparator;

  public SortTreeModel(TreeNode node, Comparator c) {
    super(node);
    comparator = c;
  }

  public SortTreeModel(TreeNode node, boolean asksAllowsChildren, Comparator c) {
    super(node, asksAllowsChildren);
    comparator = c;
  }

  public void insertNodeInto(MutableTreeNode child, MutableTreeNode parent) {
    int index = findIndexFor(child, parent);
    super.insertNodeInto(child, parent, index);
  }

  public void insertNodeInto(MutableTreeNode child, MutableTreeNode par, int i) {
    // The index is useless in this model, so just ignore it.
    insertNodeInto(child, par);
  }

  // Perform a recursive binary search on the children to find the right
  // insertion point for the next node.
  private int findIndexFor(MutableTreeNode child, MutableTreeNode parent) {
    int cc = parent.getChildCount();
    if (cc == 0) {
      return 0;
    }
    if (cc == 1) {
      return comparator.compare(child, parent.getChildAt(0)) <= 0 ? 0 : 1;
    }
    return findIndexFor(child, parent, 0, cc - 1); // First & last index
  }

  private int findIndexFor(MutableTreeNode child, MutableTreeNode parent,
      int i1, int i2) {
    if (i1 == i2) {
      return comparator.compare(child, parent.getChildAt(i1)) <= 0 ? i1
          : i1 + 1;
    }
    int half = (i1 + i2) / 2;
    if (comparator.compare(child, parent.getChildAt(half)) <= 0) {
      return findIndexFor(child, parent, i1, half);
    }
    return findIndexFor(child, parent, half + 1, i2);
  }
}
