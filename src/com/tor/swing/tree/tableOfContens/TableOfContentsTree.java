package com.tor.swing.tree.tableOfContens;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

/**
 * User: tor
 * Date: 23.06.15
 * Time: 14:34
 * http://java-swing-tips.blogspot.com/2014/01/use-jtree-as-table-of-contents.html
 */
public class TableOfContentsTree extends JTree {
    private static final BasicStroke READER = new BasicStroke(
            1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, new float[]{1f}, 0f
    );
    private boolean isSynth = false;

    public TableOfContentsTree(TreeModel newModel) {
        super(newModel);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBorder(BorderFactory.createTitledBorder("JTree#paintComponent(...)"));
        isSynth = getUI().getClass().getName().contains("Synth");
    }

    private Rectangle getVisibleRowRect() {
        Insets insets = getInsets();
        Rectangle visRectangle = getVisibleRect();
        if (visRectangle.x == 0 && visRectangle.y == 0 &&
                visRectangle.width == 0 && visRectangle.height == 0 && getVisibleRowCount() > 0) {
            visRectangle.width = 1;
            visRectangle.height = getRowHeight() * getVisibleRowCount();
        } else {
            visRectangle.x -= insets.left;
            visRectangle.y -= insets.top;
        }
//        Component component = SwingUtilities.getUnwrappedParent(this);
        Component component = SwingUtilities.getAncestorOfClass(JScrollPane.class, this);
        if (component instanceof JViewport) {
            component = component.getParent();
        }
        if (component instanceof JScrollPane) {
            JScrollPane pane = (JScrollPane) component;
            JScrollBar bar = pane.getHorizontalScrollBar();
            if (bar != null && bar.isVisible()) {
                int height = bar.getHeight();
                visRectangle.y -= height;
                visRectangle.height += height;
            }
        }

        return visRectangle;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        FontMetrics fm = g.getFontMetrics();
        int pnmaxWidth = fm.stringWidth("000");
        Insets ins = getInsets();
        Rectangle rect = getVisibleRowRect();
        for (int i = 0; i < getRowCount(); i++) {
            Rectangle r = getRowBounds(i);
            if (rect.intersects(r)) {
                TreePath path = getPathForRow(i);
                TreeCellRenderer tcr = getCellRenderer();
                JComponent c = (JComponent) tcr;
                if (isSynth && isRowSelected(i)) {
                    if (tcr instanceof DefaultTreeCellRenderer) {
                        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tcr;
                        g2.setColor(renderer.getTextSelectionColor());
                    }
                } else {
                    g2.setColor(getForeground());
                }
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object o = node.getUserObject();
                if (o instanceof TableOfContents) {
                    TableOfContents toc = (TableOfContents) o;
                    String pn = "" + toc.page;
                    int x = getWidth() - 1 - fm.stringWidth(pn) - ins.right;
                    //int y = (int)(0.5 + r.y + (r.height + fm.getAscent()) * 0.5);
                    int y = r.y + c.getBaseline(r.width, r.height);
                    g2.drawString(pn, x, y);

                    int gap = 5;
                    int x2 = getWidth() - 1 - pnmaxWidth - ins.right;
                    Stroke s = g2.getStroke();
                    g2.setStroke(READER);
                    g2.drawLine(r.x + r.width + gap, y, x2 - gap, y);
                    g2.setStroke(s);
                }
            }
        }
        g2.dispose();
    }

}
