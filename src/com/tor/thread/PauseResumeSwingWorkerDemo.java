package com.tor.thread;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public final class PauseResumeSwingWorkerDemo extends JPanel {
    private final JTextArea area = new JTextArea();
    private final JPanel statusPanel = new JPanel(new BorderLayout());
    private final JButton runButton = new JButton(new RunAction());
    private final JButton canButton = new JButton(new CancelAction());
    private final JButton pauseButton = new JButton(new PauseAction());
    private final JProgressBar bar1 = new JProgressBar();
    private final JProgressBar bar2 = new JProgressBar();
    private transient Task worker;

    public PauseResumeSwingWorkerDemo() {
        super(new BorderLayout(5, 5));
        area.setEditable(false);
        pauseButton.setEnabled(false);
        canButton.setEnabled(false);

        JPanel p = new JPanel(new GridLayout(1, 3, 2, 2));
        p.add(runButton);
        p.add(canButton);
        p.add(pauseButton);
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(p);
        box.add(Box.createHorizontalStrut(2));
        add(new JScrollPane(area));
        add(box, BorderLayout.NORTH);
        add(statusPanel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(320, 240));
    }

    private class RunAction extends AbstractAction {
        public RunAction() {
            super("run");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            //System.out.println("actionPerformed() is EDT?: " + EventQueue.isDispatchThread());
            runButton.setEnabled(false);
            canButton.setEnabled(true);
            pauseButton.setEnabled(true);
            statusPanel.add(bar1, BorderLayout.NORTH);
            statusPanel.add(bar2, BorderLayout.SOUTH);
            statusPanel.revalidate();
            //bar1.setIndeterminate(true);

            worker = new ProgressTask();
            worker.execute();
        }
    }

    private class ProgressTask extends Task {
        @Override
        protected void process(java.util.List<Progress> chunks) {
            //System.out.println("process() is EDT?: " + EventQueue.isDispatchThread());
            if (!isDisplayable()) {
                System.out.println("process: DISPOSE_ON_CLOSE");
                cancel(true);
                return;
            }
            for (Progress s : chunks) {
                switch (s.component) {
                    case TOTAL:
                        bar1.setValue((Integer) s.value);
                        break;
                    case FILE:
                        bar2.setValue((Integer) s.value);
                        break;
                    case LOG:
                        area.append((String) s.value);
                        break;
                    case PAUSE: {
                        if ((Boolean) s.value) {
                            area.append("*");
                        } else {
                            try {
                                Document doc = area.getDocument();
                                doc.remove(area.getDocument().getLength() - 1, 1);
                            } catch (BadLocationException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;
                    }
                    default:
                        throw new AssertionError("Unknown Progress");
                }
            }
        }

        @Override
        public void done() {
            if (!isDisplayable()) {
                System.out.println("done: DISPOSE_ON_CLOSE");
                cancel(true);
                return;
            }
            //System.out.println("done() is EDT?: " + EventQueue.isDispatchThread());
            runButton.requestFocusInWindow();
            runButton.setEnabled(true);
            canButton.setEnabled(false);
            pauseButton.setEnabled(false);
            statusPanel.removeAll();
            statusPanel.revalidate();
            try {
                area.append(String.format("%n%s%n", isCancelled() ? "Cancelled" : get()));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                area.append(String.format("%n%s%n", "Exception"));
            } catch (ExecutionException ex) {
                ex.printStackTrace();
                area.append(String.format("%n%s%n", "Exception"));
            }
            area.setCaretPosition(area.getDocument().getLength());
        }
    }

    private class CancelAction extends AbstractAction {
        public CancelAction() {
            super("cancel");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (worker != null && !worker.isDone()) {
                worker.cancel(true);
            }
            worker = null;
            pauseButton.setText("pause");
            pauseButton.setEnabled(false);
        }
    }

    private class PauseAction extends AbstractAction {
        public PauseAction() {
            super("pause");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            String pause = (String) getValue(Action.NAME);
            if (worker == null) {
                b.setText(pause);
            } else {
                if (worker.isCancelled() || worker.isPaused) {
                    b.setText(pause);
                } else {
                    b.setText("resume");
                }
                worker.isPaused ^= true;
            }
        }
    }
//     private void appendLine(String str) {
//         area.append(str);
//         area.setCaretPosition(area.getDocument().getLength());
//     }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("@title@");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PauseResumeSwingWorkerDemo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

enum Component {TOTAL, FILE, LOG, PAUSE}

class Progress {
    public final Object value;
    public final Component component;

    public Progress(Component component, Object value) {
        this.component = component;
        this.value = value;
    }
}

class Task extends SwingWorker<String, Progress> {
    private final Random r = new Random();
    public boolean isPaused;

    @Override
    public String doInBackground() {
        //System.out.println("doInBackground() is EDT?: " + EventQueue.isDispatchThread());
        int current = 0;
        int lengthOfTask = 12; //filelist.size();
        publish(new Progress(Component.LOG, "Length Of Task: " + lengthOfTask));
        publish(new Progress(Component.LOG, "\n------------------------------\n"));
        while (current < lengthOfTask && !isCancelled()) {
            publish(new Progress(Component.LOG, "*"));
            try {
                convertFileToSomething();
            } catch (InterruptedException ie) {
                return "Interrupted";
            }
            publish(new Progress(Component.TOTAL, 100 * current / lengthOfTask));
            current++;
        }
        publish(new Progress(Component.LOG, "\n"));
        return "Done";
    }

    private void convertFileToSomething() throws InterruptedException {
        boolean blinking = false;
        int current = 0;
        int lengthOfTask = 10 + r.nextInt(50); //long lengthOfTask = file.length();
        while (current <= lengthOfTask && !isCancelled()) {
            if (isPaused) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    return;
                }
                publish(new Progress(Component.PAUSE, blinking));
                blinking ^= true;
                continue;
            }
            int iv = 100 * current / lengthOfTask;
            Thread.sleep(20); // dummy
            publish(new Progress(Component.FILE, iv + 1));
            current++;
        }
    }
}