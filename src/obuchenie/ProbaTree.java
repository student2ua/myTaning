package obuchenie;

import obuchenie.data.DataToTest;
import obuchenie.data.Person;
import org.apache.commons.lang.CharSet;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.text.Collator;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.08.2008
 * Time: 18:25:39
 * To change this template use File | Settings | File Templates.
 */
public class ProbaTree extends JFrame {


    JTree tree;

    DefaultTreeModel treeModel;

    public ProbaTree() {
        super("Tree Test Example");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void init() {
        // Build up a bunch of TreeNodes. We use DefaultMutableTreeNode because
        // the
        // DefaultTreeModel can use it to build a complete tree.
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        // Build our tree model starting at the root node, and then make a JTree
        // out
        // of that.
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);

        // Build the tree up from the nodes we created.
        // createPainToTree(treeModel, root, DataToTest.getPersonCollection());
        //   createPainToTree2(treeModel, root, DataToTest.getPersonCollection());
        createPainToTree3true(treeModel, root, DataToTest.getPersonCollection());
        //treeModel.insertNodeInto(subroot, root, 0);
        // Or, more succinctly:
        //subroot.add(leaf1);
        //root.add(leaf2);
        tree.setRootVisible(true
        );
        // Display it.
        getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
        //  MyTreeTableModel myTreeTableModel=new MyTreeTableModel(root);
        // JTable jTable=new JTable(new TreeTableModelAdapter(myTreeTableModel,tree));
        //  getContentPane().add(new JScrollPane(jTable), BorderLayout.CENTER);
    }

    // If expanded, return only paths of nodes that are expanded.
    public TreePath[] getPaths(JTree tree, boolean expanded) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        List list = new ArrayList();
        // Traverse tree from root adding treepaths for all nodes to list
        getPaths(tree, new TreePath(root), expanded, list);
        // Convert list to array
        return (TreePath[]) list.toArray(new TreePath[list.size()]);
    }

    public void getPaths(JTree tree, TreePath parent, boolean expanded, List list) {
        // Return if node is not expanded
        if (expanded && !tree.isVisible(parent)) {
            return;
        }

        // Add node to list
        list.add(parent);

        // Create paths for all children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                getPaths(tree, path, expanded, list);
            }
        }
    }

    private void createPainToTree3true(
            final DefaultTreeModel treeModel,
            DefaultMutableTreeNode root,
            Collection paintCollection
    ) {
        ArrayList a = (ArrayList) paintCollection;
        Iterator i = a.iterator();
        Person p = null;
        Person parent = null;
        DefaultMutableTreeNode parentNode = root;
        DefaultMutableTreeNode newNode;
        while (i.hasNext()) {
            p = (Person) i.next();
            int ves, ves2;
            if
                    (parent != null) {
                parent = (Person) parentNode.getUserObject();
                ves = comparePerson(p, (Person) parentNode.getUserObject());
                ves2 = comparePersonAsString(p, (Person) parentNode.getUserObject()) + 1;
                System.out.println("ves = " + ves + " ves2= " + ves2);
                switch (ves) {
                    case 0: {
                        parentNode = root;
                        parent = null;
                        break;
                    }
                    case 1: {


                    }

                }
            }
            newNode = new DefaultMutableTreeNode(p);
            parentNode.add(newNode);
            parent = p;
            parentNode = newNode;
        }

    }

    private int comparePerson(Person p, Person person) {
        System.out.println("p = " + p);
        System.out.println("person = " + person);
        if (p.getFio().getLastName().equalsIgnoreCase(person.getFio().getLastName())) {
            if (p.getFio().getMidllName().equalsIgnoreCase(person.getFio().getMidllName())) {
                if (!p.getFio().getFirstName().equalsIgnoreCase(person.getFio().getFirstName())) {
                    return 2;                         //имена не совпали
                }
            } else return 1;         // отчества не совпали
        } else return 0; //Фамилии не совпали
        return 0;
    }

    private int comparePersonAsString(Person p, Person person) {
        String sP1 = p.getFio().getLastName() + " " + p.getFio().getMidllName() + " " + p.getFio().getFirstName();
        String sP2 = person.getFio().getLastName() + " " + person.getFio().getMidllName() + " " + person.getFio().getFirstName();
        Collator collator = Collator.getInstance(new Locale("Ru"));
        return collator.compare(sP1, sP2);
    }
       
    private void createPainToTree2(
            final DefaultTreeModel treeModel,
            DefaultMutableTreeNode root,
            Collection paintCollection
    ) {
        ArrayList a = (ArrayList) paintCollection;
        DefaultMutableTreeNode parent = root;
        System.out.println("a = " + a.size());
        Iterator i = a.iterator();
        // Person lastPerson = null;
        while (i.hasNext()) {
            Person p = (Person) i.next();

            Object[] pathTree = {
                    root, p.getFio().getLastName(), p.getFio().getMidllName(), p.getFio().getFirstName()};
            // int poz=Arrays.binarySearch(pathTree, parent.getUserObject());
            List l = Arrays.asList(pathTree);
            int poz = l.lastIndexOf(parent.getUserObject());
            while (poz > -1) {            //bvtyf yt cjdgflf.n
                while (pathTree.length > poz) {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(pathTree[poz + 1]);
                    treeModel.insertNodeInto(newNode, parent, parent.getChildCount());
                    parent = newNode;
                    poz = Arrays.binarySearch(pathTree, parent.getUserObject());
                }
            }
            //   TreePath path = new TreePath(pathTree);
            //  TreeNode[] pa = parent.getPath();
            //Object[] o = parent.getUserObjectPath();
            // if (p.getFio().getFirstName() != (o[o.length - 1])) {

//
//                treeModel.insertNodeInto(
//                        new DefaultMutableTreeNode(
//                                path.getLastPathComponent()),
//                        parent, parent.getChildCount());
            //   lastPerson = p;
            //}
        }
    }

    private void createPainToTree(final DefaultTreeModel treeModel, DefaultMutableTreeNode root, Collection c) {
        ArrayList a = (ArrayList) c;
        System.out.println("a = " + a.size());
        Iterator i = a.iterator();
        DefaultMutableTreeNode lastN = new DefaultMutableTreeNode("");
        DefaultMutableTreeNode midlN = new DefaultMutableTreeNode("");
        DefaultMutableTreeNode fistN = new DefaultMutableTreeNode("");
        while (i.hasNext()) {
            Person p = (Person) i.next();
            if (p.getFio().getLastName().equals((String) lastN.getUserObject())) {//совпала фамилия
                if (p.getFio().getMidllName().equals((String) midlN.getUserObject())) {
                    if (p.getFio().getFirstName().equals((String) fistN.getUserObject())) {
                        //    treeModel.insertNodeInto(fistN,midlN,0);
                    } else {
                        fistN = new DefaultMutableTreeNode(p.getFio().getFirstName());
                        treeModel.insertNodeInto(fistN, midlN, midlN.getChildCount());
                    }
                } else {
                    midlN = new DefaultMutableTreeNode(p.getFio().getMidllName());
                    fistN = new DefaultMutableTreeNode(p.getFio().getFirstName());
                    treeModel.insertNodeInto(midlN, lastN, lastN.getChildCount());
                    treeModel.insertNodeInto(fistN, midlN, midlN.getChildCount());

                }
            } else {
                lastN = new DefaultMutableTreeNode(p.getFio().getLastName());
                midlN = new DefaultMutableTreeNode(p.getFio().getMidllName());
                fistN = new DefaultMutableTreeNode(p.getFio().getFirstName());
                treeModel.insertNodeInto(lastN, root, root.getChildCount());
                treeModel.insertNodeInto(midlN, lastN, lastN.getChildCount());
                treeModel.insertNodeInto(fistN, midlN, midlN.getChildCount());
            }

            TreePath path = new TreePath(treeModel.getPathToRoot(fistN));
            System.out.println("path = " + path);
        }
    }

    public static void main(String args[]) {
        ProbaTree tt = new ProbaTree();
        tt.init();
        TreePath[] tp = tt.getPaths(tt.tree, false);
        System.out.println("tp = " + tp + " / length = " + tp.length);
        for (int i = 0; i < tp.length; i++) {
            System.out.println((i + 1) + " tp = " + tp[i]);
        }
        tt.setVisible(true);
    }
}

