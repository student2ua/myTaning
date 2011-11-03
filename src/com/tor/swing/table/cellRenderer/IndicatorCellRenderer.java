package com.tor.swing.table.cellRenderer;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.08.2008
 * Time: 17:04:20
 * To change this template use File | Settings | File Templates.
 */


import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @version 1.0 03/03/99
 */
public class IndicatorCellRenderer extends JProgressBar implements TableCellRenderer {
    private static final Logger log = Logger.getLogger(IndicatorCellRenderer.class);
    private Hashtable limitColors;
    private int[] limitValues;

    public IndicatorCellRenderer() {
        super(JProgressBar.HORIZONTAL);
        setBorderPainted(false);
    }

    public IndicatorCellRenderer(int min, int max) {
        super(JProgressBar.HORIZONTAL, min, max);
        setBorderPainted(false);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        int n = 0;
        if (!(value instanceof Number)) {
            String str;
            if (value instanceof String) {
                str = (String) value;
            } else {
                str = value.toString();
            }
            try {
                n = Integer.valueOf(str).intValue();
            } catch (NumberFormatException ex) {
            }
        } else {
            n = ((Number) value).intValue();
        }
        Color color = getColor(n);
        if (color != null) {
            setForeground(color);
        }
        setValue(n);
        return this;
    }

    public void setLimits(Hashtable limitColors) {
        this.limitColors = limitColors;
        int i = 0;
        int n = limitColors.size();
        limitValues = new int[n];
        Enumeration enum = limitColors.keys();
        while (enum.hasMoreElements()) {
            limitValues[i++] = ((Integer) enum.nextElement()).intValue();
        }
        sort(limitValues);
    }

    private Color getColor(int value) {
        Color color = null;
        if (limitValues != null) {
            int i;
            for (i = 0; i < limitValues.length; i++) {
                if (limitValues[i] < value) {
                    color = (Color) limitColors.get(new Integer(limitValues[i]));
                }
            }
        }
        return color;
    }

    private void sort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int k = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[k]) {
                    k = j;
                }
            }
            int tmp = a[i];
            a[i] = a[k];
            a[k] = tmp;
        }
    }
}

