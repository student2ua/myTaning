package com.tor.pattens.flyweight.v1;

import org.apache.log4j.Logger;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 15:31:47
 * To change this template use File | Settings | File Templates.
 */
public class FlyweightMain {
    private static final Logger log = Logger.getLogger(FlyweightMain.class);

    public static void main(String[] args) {
        Primitive[] primitives = {
                PrimitiveFactory.createPoint(),
                PrimitiveFactory.createCircle(5),
                PrimitiveFactory.createSquare(30, 50),
                PrimitiveFactory.createPoint(),
                PrimitiveFactory.createSquare(36, 40),
                PrimitiveFactory.createSquare(30, 50),
                PrimitiveFactory.createCircle(50)
        };
        Primitive pic = PrimitiveFactory.createPicture(primitives);
        pic.draw(new FContext(5, 10, Color.BLACK));
    }
}
