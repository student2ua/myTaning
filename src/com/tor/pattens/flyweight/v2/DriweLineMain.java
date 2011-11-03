package com.tor.pattens.flyweight.v2;

import org.apache.commons.lang.time.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 16:02:43
 * Strt.Watch = 0:00:07.938
 * HaHL.Watch = 0:00:08.266
 * FlyL.Watch = 0:00:07.953
 */
public class DriweLineMain extends JFrame {
    public static final Color[] LINE_COLOR = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE};
    public static final int WINDOW_W = 800, WINDOW_H = 600, NUMBER_OF_LINES = 1000000;

    public DriweLineMain() throws HeadlessException {
        Container parent = getContentPane();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(20, 20, WINDOW_W, WINDOW_H);
        JPanel pCommand = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton flyB = new JButton("Driwe Line FLY");
        JButton hardB = new JButton("Driwe Line Standart");
        JButton hardLB = new JButton("Driwe Line Hard with .class");
        pCommand.add(flyB);
        pCommand.add(hardB);
        pCommand.add(hardLB);
        final JPanel pintP = new JPanel();
        parent.setLayout(new BorderLayout());
        parent.add(pCommand, BorderLayout.SOUTH);
        parent.add(pintP, BorderLayout.CENTER);

        hardB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics g = pintP.getGraphics();
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                for (int i = 0; i < NUMBER_OF_LINES; ++i) {
                    g.setColor(getRandomColor());
                    g.drawLine(getRandomX(), getRandomY(),
                            getRandomX(), getRandomY());
                }
                stopWatch.stop();
                System.out.println("Strt.Watch = " + stopWatch);
            }
        });
        hardLB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics g = pintP.getGraphics();
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                for (int i = 0; i < NUMBER_OF_LINES; ++i) {
                    HeavyweightLine line = new HeavyweightLine(getRandomColor(), getRandomX(), getRandomY(), getRandomX(), getRandomY());
                    line.draw(g);
                }
                stopWatch.stop();
                System.out.println("HaHL.Watch = " + stopWatch);
            }
        });

        flyB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics g = pintP.getGraphics();
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                for (int i = 0; i < NUMBER_OF_LINES; ++i) {
                    FlyweightLine line = FlyLineFactory.createLine(getRandomColor());
                    line.draw(g, getRandomX(), getRandomY(), getRandomX(), getRandomY());
                }
                stopWatch.stop();
                System.out.println("FlyL.Watch = " + stopWatch);

            }
        });
    }

    private int getRandomY() {
        return (int) (Math.random() * WINDOW_H);
    }

    private int getRandomX() {
        return (int) (Math.random() * WINDOW_W);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DriweLineMain().setVisible(true);
            }
        });
    }

    public Color getRandomColor() {
        return LINE_COLOR[(int) (Math.random() * LINE_COLOR.length)];
    }
}
