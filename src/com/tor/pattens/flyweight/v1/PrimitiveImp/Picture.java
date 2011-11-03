package com.tor.pattens.flyweight.v1.PrimitiveImp;

import com.tor.pattens.flyweight.v1.FContext;
import com.tor.pattens.flyweight.v1.Primitive;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 14:35:53
 * Ќе раздел€емый приспособленец - изображение.
 */
public class Picture implements Primitive {
    private static final Logger log = Logger.getLogger(Picture.class);
    List primitives;//Primitive

    public Picture(Primitive[] primitives) {
        this(new LinkedList(Arrays.asList(primitives)));
    }

    public Picture(List primitives) {
        this.primitives = primitives;
    }

    public void draw(FContext context) {
        for (Iterator it = primitives.iterator(); it.hasNext();) {
            ((Primitive) it.next()).draw(context);
        }
    }
}
