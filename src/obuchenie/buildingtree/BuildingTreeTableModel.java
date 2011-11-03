package obuchenie.buildingtree;

import obuchenie.data.Building;
import obuchenie.data.Floor;
import obuchenie.data.Room;
import com.tor.swing.treetable.TreeTableModel;
import com.tor.swing.treetable.san2.AbstractTreeTableModel;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.09.2008
 * Time: 18:19:57
 * To change this template use File | Settings | File Templates.
 */
public class BuildingTreeTableModel extends AbstractTreeTableModel
        implements TreeTableModel {
    static protected Class[] cTypes = {TreeTableModel.class, Integer.class};

//    public BuildingTreeTableModel() {
//        DefaultMutableTreeNode root=new DefaultMutableTreeNode("Root");
//           DefaultTreeModel defaultTreeModel=new DefaultTreeModel(root);
//
//           toTree(defaultTreeModel,root);
//        super(new DefaultMutableTreeNode("root"));
//    }

    public BuildingTreeTableModel(Object root) {
        super(root);
    }

    public Class getColumnClass(int column) {
        if ((-1 < column) & (column < cTypes.length )) return cTypes[column];
        return Object.class;
    }

    public int getColumnCount() {
        return cTypes.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "room";
            case 1:
                return "size";
            default:
                return "xz..";
        }
    }

    public boolean isLeaf(Object node) {
        assert node instanceof DefaultMutableTreeNode;
        DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) node;
        if (treenode.getUserObject() instanceof Room) return true;
        return false;
    }

    public Object getValueAt(Object node, int column) {

        assert node instanceof DefaultMutableTreeNode;
        DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) node;
        System.out.println("treenode = " + treenode);
        switch (treenode.getLevel()) {
            case 0:
                return (column == 0) ? "root" : "";
            case 1:
                return (column == 0) ? ((Building) treenode.getUserObject()).toString() : "Floor count: " + treenode.getChildCount();
            case 2:
                return (column == 0) ? ((Floor) treenode.getUserObject()).toString() : "Room count: " + treenode.getChildCount();
            case 3:
                return (column == 0) ? ((Room) treenode.getUserObject()).toString() : ((Room) treenode.getUserObject()).getSeatingCount().toString();
            default:
                return "Error";
        }

    }

    public int getChildCount(Object parent) {
        assert parent instanceof MutableTreeNode;
        MutableTreeNode treeNode = (MutableTreeNode) parent;
        return treeNode.getChildCount();

    }

    public Object getChild(Object parent, int index) {
        assert parent instanceof MutableTreeNode;
        MutableTreeNode treeNode = (MutableTreeNode) parent;
        return treeNode.getChildAt(index);
    }
}
