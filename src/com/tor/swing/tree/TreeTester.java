package com.tor.swing.tree;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.08.2008
 * Time: 18:01:38
 * To change this template use File | Settings | File Templates.
 */
/*
Definitive Guide to Swing for Java 2, Second Edition
By John Zukowski
ISBN: 1-893115-78-X
Publisher: APress
*/

import java.awt.BorderLayout;

        import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class TreeTester {
  public static void main(String args[]) {
    JFrame f = new JFrame("Tree Dragging Tester");
    DndTree tree1 = new DndTree();
    JScrollPane leftPane = new JScrollPane(tree1);
    DndTree tree2 = new DndTree();
    JScrollPane rightPane = new JScrollPane(tree2);
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        leftPane, rightPane);
    f.getContentPane().add(splitPane, BorderLayout.CENTER);
    f.setSize(300, 200);
    f.setVisible(true);
  }
}

