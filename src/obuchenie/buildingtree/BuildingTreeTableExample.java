package obuchenie.buildingtree;

import com.tor.swing.treetable.sun.JTreeTable;
import obuchenie.data.Building;
import obuchenie.data.DataToTest;
import obuchenie.data.Floor;
import obuchenie.data.Room;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.09.2008
 * Time: 19:04:07
 * To change this template use File | Settings | File Templates.
 */
public class BuildingTreeTableExample {
    private static final Logger log = Logger.getLogger(BuildingTreeTableExample.class);

    public BuildingTreeTableExample() {
        JFrame frame = new JFrame("BuildingTreeTableExample");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(root);
        toTree(defaultTreeModel, root);
        JTreeTable treeTable = new JTreeTable(new BuildingTreeTableModel(root));
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        frame.getContentPane().add(new JScrollPane(treeTable));
        frame.pack();
        frame.show();
    }

    /**
     * @param defaultTreeModel
     * @param root
     * @see BuildingTree
     */
    public void toTree(final DefaultTreeModel defaultTreeModel, DefaultMutableTreeNode root) {
        ArrayList a = (ArrayList) DataToTest.getBuildingCollection();
        DefaultMutableTreeNode bNode;
        DefaultMutableTreeNode fNode;
        DefaultMutableTreeNode rNode;
        for (Iterator i = a.iterator(); i.hasNext();) {
            Building building = (Building) i.next();
            bNode = new DefaultMutableTreeNode(building);
            defaultTreeModel.insertNodeInto(bNode, root, root.getChildCount());

            if (building.getFloor().length > 0) {
                for (int floorCount = 0; floorCount < building.getFloor().length; floorCount++) {
                    Floor fl = (Floor) building.getFloor()[floorCount];
                    fNode = new DefaultMutableTreeNode(fl);
                    defaultTreeModel.insertNodeInto(fNode, bNode, bNode.getChildCount());

                    if (fl.getRoom().length > 0) {
                        for (int roomIterator = 0; roomIterator < fl.getRoom().length; roomIterator++) {
                            Room r = (Room) fl.getRoom()[roomIterator];
                            rNode = new DefaultMutableTreeNode(r);
                            defaultTreeModel.insertNodeInto(rNode, fNode, fNode.getChildCount());
                        }

                    }

                }
            }

        }
    }

    public static void main(String[] args) {
        new BuildingTreeTableExample();
    }
}
