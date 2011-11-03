package com.tor.pattens.flyweight.v1.PrimitiveImp;

import com.tor.pattens.flyweight.v1.FContext;
import com.tor.pattens.flyweight.v1.Primitive;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 14:30:06
 * Окружнсоть - разделяемый приспособленец. Внутреннее состояние - радиус
 */
public class Circle implements Primitive {
    private static final Logger log = Logger.getLogger(Circle.class);
    int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    public void draw(FContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
