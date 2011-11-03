package com.tor.swing.jtabbetpane;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.11.2010
 * Time: 17:10:53
 * To change this template use File | Settings | File Templates.
 */
public class TabCloseUI implements MouseListener, MouseMotionListener {
    private static final Logger log = Logger.getLogger(TabCloseUI.class);
    private CloseTabbedPane tabbedPane;
    private int closeX = 0, closeY = 0, meX = 0, meY = 0;
    private int selectedTab;

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
    }

    public void mouseClicked(MouseEvent me) {
        if (closeUnderMouse(me.getX(), me.getY())) {
            if (selectedTab == 0) {
                int choice = JOptionPane.showConfirmDialog(null, "You are about to close all tabs\n" +
                        "Do you want to proceed ?", "Confirmation Dialog", JOptionPane.INFORMATION_MESSAGE);
                switch (choice) {
                    case 0:
                        tabbedPane.removeAll();
                        break;
                    default:
                        return;
                }
            } else if (selectedTab > 0) {
                tabbedPane.removeTabAt(selectedTab);
            }
            selectedTab = tabbedPane.getSelectedIndex();
        }
    }

    public void mouseMoved(MouseEvent me) {
        meX = me.getX();
        meY = me.getY();
        if (mouseOverTab(meX, meY)) {
            controlCursor();
            tabbedPane.repaint();
        }
    }

    private void controlCursor() {
        if (tabbedPane.getTabCount() > 0)
            if (closeUnderMouse(meX, meY)) {
                tabbedPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (selectedTab > -1)
                    tabbedPane.setToolTipTextAt(selectedTab, "Close " + tabbedPane.getTitleAt(selectedTab));
            } else {
                tabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (selectedTab > -1)
                    tabbedPane.setToolTipTextAt(selectedTab, "");
            }
    }

    private boolean closeUnderMouse(int x, int y) {
        return new Rectangle(closeX - 2, closeY - 5, 10, 10).contains(x, y);
    }

    public void paint(Graphics g) {
        if (mouseOverTab(meX, meY)) {
            drawClose(g, closeX, closeY);
        }
        int tabCount = tabbedPane.getTabCount();
        for (int j = 0; j < tabCount; j++)
            if (tabbedPane.getComponent(j).isShowing()) {
                int x = tabbedPane.getBoundsAt(j).x + tabbedPane.getBoundsAt(j).width - 12;
                int y = tabbedPane.getBoundsAt(j).y + 14;
                drawClose(g, x, y);
                break;
            }
    }

    private void drawClose(Graphics g, int x, int y) {
        if (tabbedPane != null && tabbedPane.getTabCount() > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
            g2.setColor(Color.BLACK);
            if (isUnderMouse(x, y)) {
                drawColored(g2, Color.RED, x, y);
            } else {
                drawColored(g2, Color.WHITE, x, y);
            }
        }

    }

    private void drawColored(Graphics2D g2, Color color, int x, int y) {
        g2.drawLine(x, y, x + 8, y - 8);
        g2.drawLine(x + 8, y, x, y - 8);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(3, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
        g2.drawLine(x, y, x + 8, y - 8);
        g2.drawLine(x + 8, y, x, y - 8);

    }

    private boolean isUnderMouse(int x, int y) {
        if (Math.abs(x - meX + 3) < 6 && Math.abs(y - meY - 4) < 6)
            return true;
        return false;
    }

    private boolean mouseOverTab(int x, int y) {
        int tabCount = tabbedPane.getTabCount();
        for (int j = 0; j < tabCount; j++)
            if (tabbedPane.getBoundsAt(j).contains(meX, meY)) {
                selectedTab = j;
                closeX = tabbedPane.getBoundsAt(j).x + tabbedPane.getBoundsAt(j).width - 12;
                closeY = tabbedPane.getBoundsAt(j).y + 14;
                return true;
            }
        return false;
    }

    public void setTabbedPane(CloseTabbedPane ctp) {
        tabbedPane = ctp;
    }
}
