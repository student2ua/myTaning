package com.tor.pattens.bridge;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.03.2010
 * Time: 14:25:31
 * "ConcreteImplementor" 2/2 
 */
public class DrawingAPITwo implements DrawingAPI {
    private static final Logger log = Logger.getLogger(DrawingAPITwo.class);

    public void drawCircle(double x, double y, double radius) {
        System.out.println("DrawingAPITwo x= " + x + " y= " + y + "radius=" + radius);
    }
}
