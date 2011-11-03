package com.tor.pattens.flyweight.v1;

import org.apache.log4j.Logger;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 13:55:19
 * Контекст рисования, передается клиентом примитиву для отрисовки последнего
 */
public class FContext {
    private static final Logger log = Logger.getLogger(FContext.class);
    int x,y;
    Color color;

    public FContext(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
