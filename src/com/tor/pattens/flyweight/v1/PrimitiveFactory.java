package com.tor.pattens.flyweight.v1;

import com.tor.pattens.flyweight.v1.PrimitiveImp.Circle;
import com.tor.pattens.flyweight.v1.PrimitiveImp.Picture;
import com.tor.pattens.flyweight.v1.PrimitiveImp.Point;
import com.tor.pattens.flyweight.v1.PrimitiveImp.Square;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 14:43:49
 * To change this template use File | Settings | File Templates.
 */
public class PrimitiveFactory {
    private static final Logger log = Logger.getLogger(PrimitiveFactory.class);
    private static HashMap circle;  //<Integer,Circle>
    private static HashMap square;  //<Integer,Square>
    private static Point onePoint;

    static {
        circle = new HashMap();
        square = new HashMap();
    }

    public static synchronized Circle createCircle(int radius) {
        Integer iRadius = new Integer(radius);
        if (!circle.keySet().contains(iRadius)) {
            circle.put(iRadius, new Circle(radius));
        }
        return (Circle) circle.get(iRadius);
    }

    public static synchronized Square createSquare(int h, int w) {
        Integer iH = new Integer(h * 100 + w);
        if (!square.keySet().contains(iH)) {
            square.put(iH, new Square(h, w));
        }
        return (Square) square.get(iH);
    }

    public static synchronized Point createPoint() {
        if (onePoint == null) {
            onePoint = new Point();
        }
        return onePoint;
    }

    public static synchronized Picture createPicture(Primitive[] primitives) {
        return new Picture(primitives);
    }
}
