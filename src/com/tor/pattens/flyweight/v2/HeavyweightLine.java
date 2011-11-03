package com.tor.pattens.flyweight.v2;

import org.apache.log4j.Logger;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 16:47:03
 * To change this template use File | Settings | File Templates.
 */
public class HeavyweightLine {
    private static final Logger log = Logger.getLogger(HeavyweightLine.class);
    Color color = Color.BLACK;
    int x, y, x2, y2;

    public HeavyweightLine(Color color, int x, int y, int x2, int y2) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(x, y, x2, y2);
    }
}
