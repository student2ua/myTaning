package com.tor.swing.tree.tableOfContens;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * User: tor
 * Date: 23.06.15
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class TableOfContentsTreeCellRendererDotted extends DefaultTreeCellRenderer {
    private static final String READER = "... ";
    private String pn;
    private int pnx = -1, pny = -1;
    private boolean isSynth;
    private final JPanel p = new JPanel(new BorderLayout()) {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (pn != null) {
                g.setColor(isSynth ? getForeground() : getTextNonSelectionColor());
                g.drawString(pn, pnx - getX(), pny);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            d.width = Short.MAX_VALUE;
            return d;
        }
    };

    public TableOfContentsTreeCellRendererDotted() {
        super();
        p.setOpaque(false);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        isSynth = getUI().getClass().getName().contains("Synth");
        if (isSynth) {
            //System.out.println("XXX: FocusBorder bug?, JDK 1.7.0, Nimbus start LnF");
            setBackgroundSelectionColor(new Color(0x0, true));
        }
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel l = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) value;
            Object o = n.getUserObject();
            if (o instanceof TableOfContents) {
                TableOfContents toc = (TableOfContents) o;
                FontMetrics metrics = l.getFontMetrics(l.getFont());
                int gap = l.getIconTextGap();

                p.removeAll();
                p.add(l, BorderLayout.WEST);
                if (isSynth) {
                    p.setForeground(l.getForeground());
                }

                pn = String.format("%s%3d", READER, toc.page);
                pnx = tree.getWidth() - metrics.stringWidth(pn) - gap;
                //pnx = Math.max(pnx, titlex + metrics.stringWidth(pair.title) + gap);
                pny = (l.getIcon().getIconHeight() + metrics.getAscent()) / 2;

                return p;
            }
        }
        pn = null;
        return l;
    }
}
