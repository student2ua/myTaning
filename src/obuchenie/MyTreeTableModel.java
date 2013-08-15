package obuchenie;

import com.tor.swing.treetable.sun.AbstractTreeTableModel;
import com.tor.swing.treetable.sun.TreeTableModel;
import obuchenie.data.Person;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.08.2008
 * Time: 17:31:00
 * To change this template use File | Settings | File Templates.
 */
public class MyTreeTableModel extends AbstractTreeTableModel
        implements TreeTableModel {
    static protected String[] cNames = {"Person", "Age", "Sex"};

    // Types of the columns.
    static protected Class[] cTypes = {TreeTableModel.class, Integer.class, String.class};

    public MyTreeTableModel(Object root) {
        super(root);
    }

   public Class getColumnClass(int column)
    {

        return cTypes[column];

    }
    public int getColumnCount() {
        return cNames.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getColumnName(int column) {
        return cNames[column];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getValueAt(Object node, int col) {
        assert node instanceof DefaultMutableTreeNode;
        DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) node;
        System.out.println("******** treenode = "+treenode+" " + treenode.getUserObject());
            Person p = (Person) treenode.getUserObject();

        try {
            System.out.println("node = " +node +" "+node.getClass());
          switch (col) {
            case 0:
                return p.getFio().getFirstName() + " " + p.getFio().getMidllName();
            case 1:
                return p.getAge();
            case 2:
                return p.isSex() ? "Ì" : "Æ";
        }
        } catch (Exception e) {
e.printStackTrace();
        }

        return null;
    }


    public int getChildCount(Object parent) {
        assert parent instanceof MutableTreeNode;
        MutableTreeNode treeNode=(MutableTreeNode)parent;
        return treeNode.getChildCount();
    }

    public Object getChild(Object parent, int index) {
        assert parent instanceof MutableTreeNode;
        MutableTreeNode treeNode = (MutableTreeNode) parent;
        return treeNode.getChildAt(index);
    }
}
