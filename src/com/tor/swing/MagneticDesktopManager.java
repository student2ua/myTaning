package com.tor.swing;

import javax.swing.*;
import java.awt.*;

/**
 * User: tor
 * Date: 28.07.14
 * Time: 13:56
 * прилипание к краям
 */
public class MagneticDesktopManager extends DefaultDesktopManager {
    private final JDesktopPane desktop;

    public MagneticDesktopManager(JDesktopPane desktop) {
        super();
        this.desktop = desktop;
    }

    @Override
    public void dragFrame(JComponent f, int newX, int newY) {
        int w = desktop.getSize().width - f.getSize().width - newX;
        int s = desktop.getSize().height - f.getSize().height - newY;
        if (isNear(newX) || isNear(newY) || isNear(w) || isNear(s)) {
            super.dragFrame(f, getX(newX, w), getY(newY, s));
        } else {
            super.dragFrame(f, newX, newY);
        }
    }

    private int getY(int y, int i) {
        return y < i ? isNear(y) ? 0 : y : isNear(i) ? i + y : y;
    }

    private int getX(int newX, int i) {
        return newX < i ? isNear(newX) ? 0 : newX : isNear(i) ? i + newX : newX;
    }

    private boolean isNear(int i) {
        return Math.abs(i) < 10;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame();
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        frame.getContentPane().add(new DemoPanel());
                        frame.pack();
                        frame.setResizable(false);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                }
        );
    }

    private static class DemoPanel extends JPanel {
        private DemoPanel() {
            super(new BorderLayout());
            JDesktopPane pane = new JDesktopPane();
            JInternalFrame frame1 = createFrame("Frame1");
            JInternalFrame frame2 = createFrame("Frame2");
            pane.setBackground(Color.gray.brighter());
            pane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
            pane.setDesktopManager(new MagneticDesktopManager(pane));

            pane.add(frame1);
            frame1.setLocation(30, 10);
            frame1.setVisible(true);

            pane.add(frame2);
            frame2.setLocation(50, 30);
            frame2.setVisible(true);
            add(pane);
            setPreferredSize(new Dimension(320, 240));
        }

        private JInternalFrame createFrame(String title) {
            JInternalFrame frame = new JInternalFrame(title, false, false, true, true);
            frame.setSize(200, 100);
            return frame;
        }
    }
}
