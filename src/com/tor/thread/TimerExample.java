package com.tor.thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 23.12.2008
 * Time: 15:05:46
 * To change this template use File | Settings | File Templates.
 */
public class TimerExample implements ActionListener {
    private Timer timer = new Timer(100, this);
    private JLabel clockLabel;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm ss a ");
    private JProgressBar secondsProgressBar = new JProgressBar(0, 1000);

    public JPanel createPanel() {
        JPanel panel = new JPanel();
        this.clockLabel = new JLabel("Clock Stopped");
        this.clockLabel.setFont(this.clockLabel.getFont().deriveFont(Font.BOLD, 16));
        this.clockLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.secondsProgressBar.setVisible(false);
        this.secondsProgressBar.setForeground(Color.blue);
        panel.add(this.clockLabel);
        panel.add(secondsProgressBar);
        panel.add(new JButton(new ToggleClockAction()));
        return panel;
    }

    private class ToggleClockAction extends AbstractAction {
        private String startClock = "Start Clock";
        private String stopClock = "Stop Clock";
        private String clockStopped = "Clock Stopped";

        public ToggleClockAction() {
            super();
            putValue(Action.NAME, startClock);
        }

        public void actionPerformed(ActionEvent e) {
            if (TimerExample.this.timer.isRunning()) {
                putValue(Action.NAME, startClock);
                TimerExample.this.timer.stop();
                TimerExample.this.secondsProgressBar.setVisible(false);
                TimerExample.this.clockLabel.setText(clockStopped);
            } else
            {
                putValue(Action.NAME, stopClock);
//Call Action Performed
//To Initialize Time Before Timer is Started.
// Null is ok since we ignore the event.
                TimerExample.this.actionPerformed(null);
                TimerExample.this.secondsProgressBar.
                        setVisible(true);
                TimerExample.this.timer.start();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        this.calendar.setTimeInMillis(System.currentTimeMillis());
        this.clockLabel.setText(
                this.dateFormat.format(this.calendar.getTime()));
        int milliseconds = this.calendar.get(Calendar.MILLISECOND);
        this.secondsProgressBar.setValue(milliseconds);
    }

    public String getTitle() {
        return "Timer Example";
    }
      public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Demo");
                frame.getContentPane().add(new TimerExample().createPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}