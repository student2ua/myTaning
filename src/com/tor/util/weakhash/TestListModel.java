package com.tor.util.weakhash;

import org.apache.log4j.Logger;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 07.06.2010
 * Time: 20:41:32
 * To change this template use File | Settings | File Templates.
 */
public class TestListModel {
    private static final Logger log = Logger.getLogger(TestListModel.class);

    public static void main(String args[]) {
        ListDataListener ldl = new ListDataListener() {
            public void intervalAdded(ListDataEvent e) {
                System.out.println("Added: " + e);
            }

            public void intervalRemoved(ListDataEvent e) {
                System.out.println("Removed: " + e);
            }

            public void contentsChanged(ListDataEvent e) {
                System.out.println("Changed: " + e);
            }
        };
        WeakListModel model = new WeakListModel();
        model.addListDataListener(ldl);
        model.addElement("Scott McNealy");
        model.addElement("Bill Joy");
        model.addElement("Andy Bechtolsheim");
        model.addElement("Vinod Khosla");
        model.removeElement("Scott McNealy");
        ldl = null;
        System.gc();
        model.addElement("Scott McNealy");
        System.out.println(model);
    }
}
