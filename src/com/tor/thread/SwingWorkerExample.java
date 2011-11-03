package com.tor.thread;

import org.apache.log4j.Logger;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 23.12.2008
 * Time: 13:07:26
 * To change this template use File | Settings | File Templates.
 */

public class SwingWorkerExample extends JPanel {
    private JLabel statusArea;
    private DefaultListModel listModel;

    public SwingWorkerExample() {
        JButton button = new JButton(new LongRunningModelFillAction());
        add(button);

        JList list = new JList();
        this.listModel = new DefaultListModel();
        this.listModel.addElement("An Empty List Model");
        list.setModel(listModel);
        list.setFixedCellWidth(80);
        add(new JScrollPane(list));

        add(new JLabel("Status:"));
        this.statusArea = new JLabel();
        this.statusArea.setSize(80,30);
        add(this.statusArea);
        setSize(800,600);
    }

    private class LongRunningModelFillAction extends AbstractAction {
        public LongRunningModelFillAction() {
            super("Fill Model");
        }

        public void actionPerformed(ActionEvent e) {
            PopulationWorker populationWorker = new PopulationWorker();
            populationWorker.start();
        }
    }

    private class PopulationWorker extends SwingWorker {
        public Object construct() {
            Object[] values = new Object[1000];
            for (int i = 1; i <= 1000; i++) {
                values[i - 1] = "Value" + i;

                if ((i % 10) == 0) {
                    final int progress = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            SwingWorkerExample.this.statusArea.setText("Calculated " + progress);
                        }
                    });
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }
            return values;
        }

        public void start() {
            SwingWorkerExample.this.listModel.removeAllElements();
            SwingWorkerExample.this.listModel.addElement("Calculating...");
            super.start();
        }

        public void finished() {
            SwingWorkerExample.this.statusArea.setText("");
            SwingWorkerExample.this.listModel.removeAllElements();
            Object[] values = (Object[]) getValue();
            for (int i = 0; i < values.length; i++) {
                SwingWorkerExample.this.listModel.addElement(values[i]);
            }
        }
    }
    public static void main(String[] a){
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(1);
      f.getContentPane().add(new SwingWorkerExample());
      f.pack();
      f.setVisible(true);
    }

}



