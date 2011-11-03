package com.tor.pattens.flyweight.v1.PrimitiveImp;

import com.tor.pattens.flyweight.v1.FContext;
import com.tor.pattens.flyweight.v1.Primitive;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 13:59:07
 * Разделяемый приспособленец - Квадрат. Внутренее состояние - высота, ширина.
 */
public class Square implements Primitive {
    private static final Logger log = Logger.getLogger(Square.class);
    int height, width;

    public Square(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void draw(FContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
