package com.tor.swing.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.08.2008
 * Time: 18:28:53
 * To change this template use File | Settings | File Templates.
 */
public class TransferableTreeNode extends DefaultMutableTreeNode implements
        Transferable {
  final static int TREE = 0;

  final static int STRING = 1;

  final static int PLAIN_TEXT = 1;

  final public static DataFlavor DEFAULT_MUTABLE_TREENODE_FLAVOR = new DataFlavor(
      DefaultMutableTreeNode.class, "Default Mutable Tree Node");

  static DataFlavor flavors[] = { DEFAULT_MUTABLE_TREENODE_FLAVOR,
      DataFlavor.stringFlavor, DataFlavor.plainTextFlavor };

  private DefaultMutableTreeNode data;

  public TransferableTreeNode(DefaultMutableTreeNode data) {
    this.data = data;
  }

  public DataFlavor[] getTransferDataFlavors() {
    return flavors;
  }

  public Object getTransferData(DataFlavor flavor)
      throws UnsupportedFlavorException, IOException {
    Object returnObject;
    if (flavor.equals(flavors[TREE])) {
      Object userObject = data.getUserObject();
      if (userObject == null) {
        returnObject = data;
      } else {
        returnObject = userObject;
      }
    } else if (flavor.equals(flavors[STRING])) {
      Object userObject = data.getUserObject();
      if (userObject == null) {
        returnObject = data.toString();
      } else {
        returnObject = userObject.toString();
      }
    } else if (flavor.equals(flavors[PLAIN_TEXT])) {
      Object userObject = data.getUserObject();
      String string;
      if (userObject == null) {
        string = data.toString();
      } else {
        string = userObject.toString();
      }
      returnObject = new ByteArrayInputStream(string.getBytes("Unicode"));
    } else {
      throw new UnsupportedFlavorException(flavor);
    }
    return returnObject;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    boolean returnValue = false;
    for (int i = 0, n = flavors.length; i < n; i++) {
      if (flavor.equals(flavors[i])) {
        returnValue = true;
        break;
      }
    }
    return returnValue;
  }
}
