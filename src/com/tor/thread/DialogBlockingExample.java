package com.tor.thread;
/*
Code from Desktop Java Live Source

URL: http://www.sourcebeat.com/downloads/

*/



import javax.swing.*;
import java.awt.event.ActionEvent;

public class DialogBlockingExample {
    public static JPanel createPanel() {
        JPanel panel = new JPanel();

        JButton button = new JButton(new DialogAction(panel));
        panel.add(button);

        return panel;
    }

    private static class DialogAction extends AbstractAction {
        private JPanel panel;

        public DialogAction(JPanel panel) {
            super("Open Dialog");
            this.panel = panel;
        }

        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), true);
            dialog.getContentPane().add(new JLabel("A Dialog"));
            dialog.pack();
            dialog.setVisible(true);
            putValue(Action.NAME, "Dialog Already Shown");
        }
    }

    public static void main(String[] a){
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(1);
      f.getContentPane().add(createPanel());
      f.pack();
      f.setVisible(true);
    }

}