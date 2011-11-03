package com.tor.pattens.flyweight.v2;

import org.apache.log4j.Logger;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 16:58:01
 * To change this template use File | Settings | File Templates.
 */
public class FlyweightLine {
    private static final Logger log = Logger.getLogger(FlyweightLine.class);
    Color color = Color.BLACK;

    public FlyweightLine(Color color) {
        this.color = color;
    }

    public void draw(Graphics g, int x, int y, int x2, int y2) {
        g.setColor(color);
        g.drawLine(x, y, x2, y2);
    }
}
