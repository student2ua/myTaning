package com.tor.swing;
import com.tor.swing.treetable.JTreeTable;
import com.tor.swing.treetable.TreeTableModel;
import com.tor.swing.treetable.san2.AbstractTreeTableModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
public class TreeTableEasyExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        DefaultMutableTreeNode root = getExampleFamily();
        TreeTableModel model = (TreeTableModel) new MyTreeTableModel(root);
        JTreeTable treeTable = new JTreeTable(model);
        treeTable.getTree().setRootVisible(false);
        frame.getContentPane().add(new JScrollPane(treeTable));
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private static class MyTreeTableModel extends AbstractTreeTableModel {
        public MyTreeTableModel(Object root) { super(root);   }

        /**
         * Error in AbstractTreeTableModel !!!
         * Without overriding this method you can't expand the tree!
         */
        public Class getColumnClass(int column) {
            switch (column) {
                case 0:
                    return TreeTableModel.class;
                default:
                    return Object.class;
            }
        }

        public Object getChild(Object parent, int index) {
            assert parent instanceof MutableTreeNode;
            MutableTreeNode treenode = (MutableTreeNode) parent;
            return treenode.getChildAt(index);
        }

        public int getChildCount(Object parent) {
            assert parent instanceof MutableTreeNode;
            MutableTreeNode treenode = (MutableTreeNode) parent;
            return treenode.getChildCount();
        }
        public int getColumnCount() {
            return 3;
        }
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Surname";
                case 1:
                    return "Firstname";
                 case 2:
                    return "Age";
                default:
                    return null;
            }
        }

        public Object getValueAt(Object node, int column) {
            assert node instanceof DefaultMutableTreeNode;
            DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) node;
            if ( treenode.getUserObject()instanceof Person){
            Person person = (Person) treenode.getUserObject();
            switch (column) {
                case 1:
                    return person.firstname;
                case 2:
                    return person.vAge;
                default:
                    return null;
            }

        }
            return treenode.getUserObject();
        }
    }

    private static class Person {
        private String surname;
        private String firstname;
        private Integer vAge;
        public Person(String surname, String firstname, Integer vAge) {
            this.surname = surname;
            this.firstname = firstname;
            this.vAge  =vAge;
        }

        /**
         * @see DefaultMutableTreeNode#toString()
         */
        public String toString() {
            return surname;
        }

    }

    private static DefaultMutableTreeNode getExampleFamily() {
        Person grandmother = new Person("SchmitzGM", "Sabine",new Integer(92));
        Person mother = new Person("SchmitzM", "Christiane",new Integer(45));
        Person father = new Person("SchmitzF", "Paul",new Integer(46));
        Person daughter = new Person("SchmitzD", "Laura",new Integer(25));
        Person son = new Person("SchmitzS", "Flo",new Integer(20));
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(grandmother);
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode(father);
        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode(mother);
        root.add(child1);
        root.add(child2);
        DefaultMutableTreeNode grandchild1a = new DefaultMutableTreeNode(daughter);
        DefaultMutableTreeNode grandchild2a = new DefaultMutableTreeNode(son);
        DefaultMutableTreeNode grandchild1b = new DefaultMutableTreeNode(daughter);
        DefaultMutableTreeNode grandchild2b = new DefaultMutableTreeNode(son);
        child1.add(grandchild1a);
        child1.add(grandchild2a);
        child2.add(grandchild1b);
        child2.add(grandchild2b);
        return root;
    }
}
