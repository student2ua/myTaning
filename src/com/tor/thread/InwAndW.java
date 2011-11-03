package com.tor.thread;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 23.12.2008
 * Time: 14:11:21
 * Зырить майн метод и выполнение запроса на обнуление прогресс бара
 */
public class InwAndW {
    private static final Logger log = Logger.getLogger(InwAndW.class);
    private Thread progressThread;
    private JProgressBar progressBar;
    private boolean keepRunning;

    public JPanel createPanel() {
        this.keepRunning = true;
        this.progressThread = new ProgressThread();
        JPanel panel = new JPanel();
        // panel.setLayout(new FormLayout("p:g, p, p:g", "p:g, p, p:g"));
        //  CellConstraints cc = new CellConstraints();
        this.progressBar = new JProgressBar();
        this.progressBar.setMaximum(100);
        panel.add(this.progressBar);//, cc.xy(2, 2)
        System.out.println(keepRunning);
        this.progressThread.start();
        return panel;
    }

    public String getTitle() {
        return "InvokeAndWait Example";
    }

    public void endExample() {
        this.keepRunning = false;
    }

    private class ProgressThread extends Thread {
        public void run() {
            int count = 0;
            while (keepRunning) {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                }

                final int cval = count;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        progressBar.setValue(cval);
                    }
                });

                count++;
                if (count == 101) {
                    count = 0;
                } else if (count == 50) {
                    final int[] returnValue = new int[1];
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                returnValue[0] = JOptionPane.showConfirmDialog(
                                        progressBar, "Would you like to reset progress ? ", "Prompt", JOptionPane.YES_NO_OPTION);
                            }
                        });
                        if (returnValue[0] == JOptionPane.YES_OPTION) {
                            count = 0;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Demo");
                frame.getContentPane().add(new InwAndW().createPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
