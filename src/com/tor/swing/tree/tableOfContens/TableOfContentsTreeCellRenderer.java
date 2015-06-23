package com.tor.swing.tree.tableOfContens;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * User: tor
 * Date: 23.06.15
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class TableOfContentsTreeCellRenderer extends DefaultTreeCellRenderer {
    private static final BasicStroke READER =
            new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, new float[]{1f}, 0f);
    private String pn;
    private boolean isSynth;
    private final Point pnPt = new Point();
    private int rxs, rxe;
    private final JPanel p = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (pn != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(isSynth ? getForeground() : getTextNonSelectionColor());
                g2.drawString(pn, pnPt.x - getX(), pnPt.y);
                g2.setStroke(READER);
                g2.drawLine(rxs, pnPt.y, rxe - getX(), pnPt.y);
                g2.dispose();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            d.width = Short.MAX_VALUE;
            return d;
        }
    };

    public TableOfContentsTreeCellRenderer() {
        super();
        p.setOpaque(false);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        isSynth = getUI().getClass().getName().contains("Synth");
        if (isSynth) {
            setBackgroundSelectionColor(new Color(0x0, true));
        }
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);    //To change body of overridden methods use File | Settings | File Templates.
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object o = node.getUserObject();
            if (o instanceof TableOfContents) {
                TableOfContents tableOfContents = (TableOfContents) o;
                FontMetrics metrics = label.getFontMetrics(label.getFont());
                int gap = label.getIconTextGap();
                Dimension dimension = label.getPreferredSize();
                Insets ins = tree.getInsets();

                p.removeAll();
                p.add(label, BorderLayout.WEST);
                if (isSynth) {
                    p.setForeground(label.getForeground());
                }

                pn = String.format("%3d", tableOfContents.page);
                pnPt.x = tree.getWidth() - metrics.stringWidth(pn) - gap;
                pnPt.y = label.getBaseline(dimension.width, dimension.height);

                rxs = dimension.width + gap;
                rxe = tree.getWidth() - ins.right - metrics.stringWidth("000") - gap;
                return p;
            }
        }
        pn = null;
        return label;
    }

}
