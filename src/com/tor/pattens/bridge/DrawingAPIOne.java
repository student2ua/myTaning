package com.tor.pattens.bridge;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.03.2010
 * Time: 14:20:54
 * "ConcreteImplementor" 1/2 
 */
public class DrawingAPIOne implements DrawingAPI {
    private static final Logger log = Logger.getLogger(DrawingAPIOne.class);

    public void drawCircle(double x, double y, double radius) {
        System.out.println("DrawingAPIOne x= " + x + " y= " + y + "radius= " + radius);
    }
}
