package com.tor.thread;

import javax.swing.*;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.05.13
 * Time: 19:00
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://stackoverflow.com/questions/10236995/how-do-i-make-my-swingworker-example-work-properly
 */
@SuppressWarnings("serial")
public class SwingWorker_jdk6 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SwingWorker_jdk6().createAndShowGui();
            }
        });
    }

    public void createAndShowGui() {
        final JFrame frame = new JFrame("SwingTesting2");
        final JDialog dialog = new JDialog(frame, "Dialog", ModalityType.APPLICATION_MODAL);
        final DialogPanel dialogPanel = new DialogPanel();
        dialog.getContentPane().add(dialogPanel.getMainPanel());
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

        JButton button = new JButton(new AbstractAction("Test Me") {

            @Override
            public void actionPerformed(ActionEvent actEvt) {
                final GuiWorker2 guiWorker = new GuiWorker2();
                guiWorker.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent pcEvt) {
                        if ("state".equals(pcEvt.getPropertyName())) {
                            if (pcEvt.getNewValue() == SwingWorker.StateValue.DONE) {
                                try {
                                    dialogPanel.done(guiWorker.get());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if ("progress".equals(pcEvt.getPropertyName())) {
                            dialogPanel.setProgress((Integer) pcEvt.getNewValue());
                        }
                    }
                });

                guiWorker.execute();
                dialogPanel.start();
                dialog.setVisible(true);
            }
        });

        //noinspection MagicConstant
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    class GuiWorker2 extends SwingWorker<Integer, Integer> {
        private static final int MAX_COUNT = 20;
        private static final long SLEEP_TIME = 100;
        private int count = 0;

        @Override
        protected Integer doInBackground() throws Exception {
            while (count < MAX_COUNT) {
                Thread.sleep(SLEEP_TIME);
                count++;
                setProgress((100 * count) / MAX_COUNT);
            }
            return count;
        }
    }

    @SuppressWarnings("serial")
    class DialogPanel {
        public static final String PROGRESS_BAR = "Progress Bar";
        public static final String DONE = "Done";
        private static final int TIMER_DELAY = 2000;
        private CardLayout cardLayout = new CardLayout();
        private JPanel mainPanel = new JPanel(cardLayout);
        private JLabel doneLabel = new JLabel("Done", JLabel.CENTER);
        private JProgressBar progressBar = new JProgressBar();

        public DialogPanel() {
            progressBar.setString("Waiting on time");
            progressBar.setStringPainted(true);
            progressBar.setIndeterminate(false);

            mainPanel.add(progressBar, PROGRESS_BAR);
            mainPanel.add(doneLabel, DONE);
        }

        public void setProgress(Integer newValue) {
            progressBar.setValue(newValue);
        }

        public void start() {
            cardLayout.show(mainPanel, PROGRESS_BAR);
            progressBar.setValue(0);
        }

        public void done(int countValue) {
            doneLabel.setText(DONE + ". Count: " + countValue);
            cardLayout.show(mainPanel, DONE);
            new Timer(TIMER_DELAY, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Window win = SwingUtilities.getWindowAncestor(mainPanel);
                    win.dispose();
                }
            }) {{
                setRepeats(false);
            }}.start();
        }

        public JPanel getMainPanel() {
            return mainPanel;
        }

    }
}
