package com.tor.swing.treetable;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.08.2008
 * Time: 14:02:58
 * To change this template use File | Settings | File Templates.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class TreeIt {

    class MyCellRenderer extends JLabel implements TreeCellRenderer {
        MyCellRenderer() {
            setOpaque(true);
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded, boolean leaf, int row,
                                                      boolean hasFocus) {
            StringBuffer text = new StringBuffer();
            if (expanded) {
                text.append("E:");
            } else {
                text.append("  ");
            }
            if (leaf) {
                text.append("L:");
            } else {
                text.append("  ");
            }
            if (hasFocus) {
                text.append("H:");
            } else {
                text.append("  ");
            }
            text.append(row + "->");
            text.append(value.toString());
            System.out.println(text+" text.length() = " + text.toString().length());

            setBackground(selected ? Color.BLUE : Color.YELLOW);
            setForeground(selected ? Color.YELLOW : Color.BLUE);

            setText(text.toString());
            return this;
        }
    }

    public TreeIt() {
        JFrame f = new JFrame();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Calendar");
        DefaultMutableTreeNode months = new DefaultMutableTreeNode("Months");
        root.add(months);
        String monthLabels[] = {"January", "February", "March", "April",
                "May", "June", "July", "August", "September", "October",
                "November", "December"};
        for (int i = 0, n = monthLabels.length; i < n; i++)
            months.add(new DefaultMutableTreeNode(monthLabels[i]));
        DefaultMutableTreeNode weeks = new DefaultMutableTreeNode("Weeks");
        root.add(weeks);
        String weekLabels[] = {"Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};
        for (int i = 0, n = weekLabels.length; i < n; i++)
            weeks.add(new DefaultMutableTreeNode(weekLabels[i]));
        JTree jt = new JTree(root);
        jt.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getPath();
                System.out.println("Выбран: " + path.getLastPathComponent());
                Object elements[] = path.getPath();
                for (int i = 0, n = elements.length; i < n; i++) {
                    System.out.print("->" + elements[i]);
                }
                System.out.println();
            }
        });

        DefaultMutableTreeNode lastLeaf = root.getLastLeaf();
        TreePath path = new TreePath(lastLeaf.getPath());
        jt.setSelectionPath(path);

        jt.setCellRenderer(new MyCellRenderer());

        JScrollPane jsp = new JScrollPane(jt);
        Container c = f.getContentPane();
        c.add(jsp, BorderLayout.CENTER);
        f.setSize(250, 250);
        f.show();
    }

    public static void main(String args[]) {
        new TreeIt();
    }
}