package com.tor.pattens.bridge;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.03.2010
 * Time: 14:40:03
 * "Client"
 */
public class BridgePattern {
    private static final Logger log = Logger.getLogger(BridgePattern.class);

    public static void main(String[] args) {
        BridgeShape[] shapes = new BridgeShape[2];
        shapes[0] = new CircleShape(1, 2, 3, new DrawingAPIOne());
        shapes[1] = new CircleShape(4, 5, 6, new DrawingAPITwo());
        for (int i = 0; i < shapes.length; i++) {
            BridgeShape ref = shapes[i];
            ref.resizeByPercentage(0.5d);
            ref.draw();
        }
    }
}
