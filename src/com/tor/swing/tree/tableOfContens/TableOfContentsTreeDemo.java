package com.tor.swing.tree.tableOfContens;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public final class TableOfContentsTreeDemo extends JPanel {
    private TableOfContentsTreeDemo() {
        super(new BorderLayout());

        JTree tree = new JTree(makeModel()) {
            @Override
            public boolean getScrollableTracksViewportWidth() { //NOPMD A getX() method which returns a boolean should be named isX()
                return true;
            }

            @Override
            public void updateUI() {
                super.updateUI();
//                setCellRenderer(new TableOfContentsTreeCellRendererDotted());
                setCellRenderer(new TableOfContentsTreeCellRenderer());
                setBorder(BorderFactory.createTitledBorder("TreeCellRenderer"));
            }
        };
        tree.setRootVisible(false);

        JTree tree2 = new TableOfContentsTree(makeModel());
        tree2.setRootVisible(false);
        //tree2.setLargeModel(false);

        JSplitPane sp = new JSplitPane();
        sp.setResizeWeight(.5);
        sp.setLeftComponent(new JScrollPane(tree));
        sp.setRightComponent(new JScrollPane(tree2));
        add(sp);
        setPreferredSize(new Dimension(320, 240));
    }

    private static DefaultTreeModel makeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode s0 = new DefaultMutableTreeNode(new TableOfContents("1. Introductiion", 1));
        DefaultMutableTreeNode s1 = new DefaultMutableTreeNode(new TableOfContents("2. Chapter", 1));
        s1.add(new DefaultMutableTreeNode(new TableOfContents("2.1. Section", 2)));
        s1.add(new DefaultMutableTreeNode(new TableOfContents("2.2. Section", 4)));
        s1.add(new DefaultMutableTreeNode(new TableOfContents("2.3. Section", 8)));

        DefaultMutableTreeNode s2 = new DefaultMutableTreeNode(new TableOfContents("3. Chapter", 10));
        s2.add(new DefaultMutableTreeNode(new TableOfContents("ddddddd", 12)));
        s2.add(new DefaultMutableTreeNode(new TableOfContents("eee", 24)));
        s2.add(new DefaultMutableTreeNode(new TableOfContents("f", 38)));

        root.add(s0);
        root.add(s1);
        root.add(s2);
        return new DefaultTreeModel(root);
    }

    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("@title@");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TableOfContentsTreeDemo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
