package com.tor.reflection.infoofclass;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.01.2009
 * Time: 15:52:35
 * To change this template use File | Settings | File Templates.
 */
public class Point implements IPoint {
    private static final Logger log = Logger.getLogger(Point.class);

    public final static String DEFAULT_NAME = "Point";
    public static int MAX_X_VALUE = 700;
    public static int MAX_Y_VALUE = 500;
    private String name;
    private int x;
    private int y;

    public Point(String name, int x, int y) {
        this(x, y);
        this.name = name;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
