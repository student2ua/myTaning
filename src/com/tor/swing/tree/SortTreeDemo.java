package com.tor.swing.tree;
/*
Java Swing, 2nd Edition
By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
ISBN: 0-596-00408-7
Publisher: O'Reilly
*/
// SortTreeDemo.java
//This class creates a tree model using the SortTreeModel with
//a File hierarchy as input.
//

import java.io.File;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class SortTreeDemo extends JFrame {
  public SortTreeDemo(String startDir) {
    super("SortTreeModel Demonstration");
    setSize(300, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    PrettyFile f = new PrettyFile(startDir);
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(f);
    SortTreeModel model = new SortTreeModel(root,
        new TreeStringComparator());
    fillModel(model, root);

    JTree tree = new JTree(model);
    getContentPane().add(new JScrollPane(tree));
  }

  protected void fillModel(SortTreeModel model, DefaultMutableTreeNode current) {
    PrettyFile pf = (PrettyFile) current.getUserObject();
    File f = pf.getFile();
    if (f.isDirectory()) {
      String files[] = f.list();
      // ignore "." files
      for (int i = 0; i < files.length; i++) {
        if (files[i].startsWith("."))
          continue;
        PrettyFile tmp = new PrettyFile(pf, files[i]);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(tmp);
        model.insertNodeInto(node, current);
        if (tmp.getFile().isDirectory()) {
          fillModel(model, node);
        }
      }
    }
  }

  public class PrettyFile {
    File f;

    public PrettyFile(String s) {
      f = new File(s);
    }

    public PrettyFile(PrettyFile pf, String s) {
      f = new File(pf.f, s);
    }

    public File getFile() {
      return f;
    }

    public String toString() {
      return f.getName();
    }
  }

  public static void main(String args[]) {
    SortTreeDemo demo = new SortTreeDemo(args.length == 1 ? args[0] : ".");
    demo.setVisible(true);
  }
}


//TreeStringComparator.java
//This class compares the contents of the userObject as strings.
//It's case-insensitive.
//

class TreeStringComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    if (!(o1 instanceof DefaultMutableTreeNode && o2 instanceof DefaultMutableTreeNode)) {
      throw new IllegalArgumentException(
          "Can only compare DefaultMutableTreeNode objects");
    }
    String s1 = ((DefaultMutableTreeNode) o1).getUserObject().toString();
    String s2 = ((DefaultMutableTreeNode) o2).getUserObject().toString();
    return s1.compareToIgnoreCase(s2);
  }
}