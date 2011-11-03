package com.tor.pattens.bridge;

import org.apache.log4j.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.03.2010
 * Time: 14:28:11
 * "Refined Abstraction"
 */
public class CircleShape implements BridgeShape {
    private static final Logger log = Logger.getLogger(CircleShape.class);
    double x, y, radius;
    DrawingAPI drawingAPI;

    public CircleShape(double x, double y, double radius, DrawingAPI drawingAPI) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.drawingAPI = drawingAPI;
    }

    public void draw()                             // low-level
    {
        drawingAPI.drawCircle(x, y, radius);
    }

    public void resizeByPercentage(double pct)     // high-level
    {
        radius *= pct;
    }
}
