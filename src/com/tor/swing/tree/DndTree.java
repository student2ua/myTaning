package com.tor.swing.tree;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.dnd.*;
import java.awt.*;
import java.awt.List;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.util.*;

import com.tor.swing.tree.TransferableTreeNode;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.08.2008
 * Time: 18:28:51
 * To change this template use File | Settings | File Templates.
 */
class DndTree extends JTree implements Autoscroll {

  private Insets insets;

  private int top = 0, bottom = 0, topRow = 0, bottomRow = 0;

  public DndTree() {
    DragSource dragSource = DragSource.getDefaultDragSource();
    dragSource
        .createDefaultDragGestureRecognizer(this,
            DnDConstants.ACTION_COPY_OR_MOVE,
            new DndTree.TreeDragGestureListener());
    DropTarget dropTarget = new DropTarget(this,
        new DndTree.TreeDropTargetListener());
  }

  public DndTree(TreeModel model) {
    super(model);
    DragSource dragSource = DragSource.getDefaultDragSource();
    dragSource
        .createDefaultDragGestureRecognizer(this,
            DnDConstants.ACTION_COPY_OR_MOVE,
            new DndTree.TreeDragGestureListener());
    DropTarget dropTarget = new DropTarget(this,
        new DndTree.TreeDropTargetListener());
  }

  public Insets getAutoscrollInsets() {
    return insets;
  }

  public void autoscroll(Point p) {
    // Only support up/down scrolling
    top = Math.abs(getLocation().y) + 10;
    bottom = top + getParent().getHeight() - 20;
    int next;
    if (p.y < top) {
      next = topRow--;
      bottomRow++;
      scrollRowToVisible(next);
    } else if (p.y > bottom) {
      next = bottomRow++;
      topRow--;
      scrollRowToVisible(next);
    }
  }

  private static class TreeDragGestureListener implements DragGestureListener {
    public void dragGestureRecognized(DragGestureEvent dragGestureEvent) {
      // Can only drag leafs
      JTree tree = (JTree) dragGestureEvent.getComponent();
      TreePath path = tree.getSelectionPath();
      if (path == null) {
        // Nothing selected, nothing to drag
        System.out.println("Nothing selected - beep");
        tree.getToolkit().beep();
      } else {
        DefaultMutableTreeNode selection = (DefaultMutableTreeNode) path
            .getLastPathComponent();
        if (selection.isLeaf()) {
          TransferableTreeNode node = new TransferableTreeNode(
              selection);
          dragGestureEvent.startDrag(DragSource.DefaultCopyDrop,
              node, new DndTree.MyDragSourceListener());
        } else {
          System.out.println("Not a leaf - beep");
          tree.getToolkit().beep();
        }
      }
    }
  }

  private class TreeDropTargetListener implements DropTargetListener {

    public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
      // Setup positioning info for auto-scrolling
      top = Math.abs(getLocation().y);
      bottom = top + getParent().getHeight();
      topRow = getClosestRowForLocation(0, top);
      bottomRow = getClosestRowForLocation(0, bottom);
      insets = new Insets(top + 10, 0, bottom - 10, getWidth());
    }

    public void dragExit(DropTargetEvent dropTargetEvent) {
    }

    public void dragOver(DropTargetDragEvent dropTargetDragEvent) {
    }

    public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {
    }

    public synchronized void drop(DropTargetDropEvent dropTargetDropEvent) {
      // Only support dropping over nodes that aren't leafs

      Point location = dropTargetDropEvent.getLocation();
      TreePath path = getPathForLocation(location.x, location.y);
      Object node = path.getLastPathComponent();
      if ((node != null) && (node instanceof TreeNode)
          && (!((TreeNode) node).isLeaf())) {
        try {
          Transferable tr = dropTargetDropEvent.getTransferable();
          if (tr
              .isDataFlavorSupported(TransferableTreeNode.DEFAULT_MUTABLE_TREENODE_FLAVOR)) {
            dropTargetDropEvent
                .acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            Object userObject = tr
                .getTransferData(TransferableTreeNode.DEFAULT_MUTABLE_TREENODE_FLAVOR);
            addElement(path, userObject);
            dropTargetDropEvent.dropComplete(true);
          } else if (tr
              .isDataFlavorSupported(DataFlavor.stringFlavor)) {
            dropTargetDropEvent
                .acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            String string = (String) tr
                .getTransferData(DataFlavor.stringFlavor);
            addElement(path, string);
            dropTargetDropEvent.dropComplete(true);
          } else if (tr
              .isDataFlavorSupported(DataFlavor.plainTextFlavor)) {
            dropTargetDropEvent
                .acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            Object stream = tr
                .getTransferData(DataFlavor.plainTextFlavor);
            if (stream instanceof InputStream) {
              InputStreamReader isr = new InputStreamReader(
                  (InputStream) stream);
              BufferedReader reader = new BufferedReader(isr);
              String line;
              while ((line = reader.readLine()) != null) {
                addElement(path, line);
              }
              dropTargetDropEvent.dropComplete(true);
            } else if (stream instanceof Reader) {
              BufferedReader reader = new BufferedReader(
                  (Reader) stream);
              String line;
              while ((line = reader.readLine()) != null) {
                addElement(path, line);
              }
              dropTargetDropEvent.dropComplete(true);
            } else {
              System.err.println("Unknown type: "
                  + stream.getClass());
              dropTargetDropEvent.rejectDrop();
            }
          } else if (tr
              .isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            dropTargetDropEvent
                .acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            java.util.List fileList = (java.util.List) tr
                .getTransferData(DataFlavor.javaFileListFlavor);
            Iterator iterator = fileList.iterator();
            while (iterator.hasNext()) {
              File file = (File) iterator.next();
              addElement(path, file.toURL());
            }
            dropTargetDropEvent.dropComplete(true);
          } else {
            System.err.println("Rejected");
            dropTargetDropEvent.rejectDrop();
          }
        } catch (IOException io) {
          io.printStackTrace();
          dropTargetDropEvent.rejectDrop();
        } catch (UnsupportedFlavorException ufe) {
          ufe.printStackTrace();
          dropTargetDropEvent.rejectDrop();
        }
      } else {
        System.out.println("Can't drop on a leaf");
        dropTargetDropEvent.rejectDrop();
      }
    }

    private void addElement(TreePath path, Object element) {
      DefaultMutableTreeNode parent = (DefaultMutableTreeNode) path
          .getLastPathComponent();
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(element);
      System.out.println("Added: " + node + " to " + parent);
      DefaultTreeModel model = (DefaultTreeModel) (DndTree.this
          .getModel());
      model.insertNodeInto(node, parent, parent.getChildCount());
    }
  }

  private static class MyDragSourceListener implements DragSourceListener {
    public void dragDropEnd(DragSourceDropEvent dragSourceDropEvent) {
      if (dragSourceDropEvent.getDropSuccess()) {
        int dropAction = dragSourceDropEvent.getDropAction();
        if (dropAction == DnDConstants.ACTION_MOVE) {
          System.out.println("MOVE: remove node");
        }
      }
    }

    public void dragEnter(DragSourceDragEvent dragSourceDragEvent) {
      DragSourceContext context = dragSourceDragEvent
          .getDragSourceContext();
      int dropAction = dragSourceDragEvent.getDropAction();
      if ((dropAction & DnDConstants.ACTION_COPY) != 0) {
        context.setCursor(DragSource.DefaultCopyDrop);
      } else if ((dropAction & DnDConstants.ACTION_MOVE) != 0) {
        context.setCursor(DragSource.DefaultMoveDrop);
      } else {
        context.setCursor(DragSource.DefaultCopyNoDrop);
      }
    }

    public void dragExit(DragSourceEvent dragSourceEvent) {
    }

    public void dragOver(DragSourceDragEvent dragSourceDragEvent) {
    }

    public void dropActionChanged(DragSourceDragEvent dragSourceDragEvent) {
    }
  }
}
