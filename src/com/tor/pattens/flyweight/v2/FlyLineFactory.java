package com.tor.pattens.flyweight.v2;

import org.apache.log4j.Logger;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2010
 * Time: 17:04:05
 * To change this template use File | Settings | File Templates.
 */
public class FlyLineFactory {
    private static final Logger log = Logger.getLogger(FlyLineFactory.class);
    private static HashMap lineByColor = new HashMap();

    public static FlyweightLine createLine(Color color) {
        if (!lineByColor.containsKey(color)) {
            lineByColor.put(color, new FlyweightLine(color));
        }
        return (FlyweightLine) lineByColor.get(color);
    }
}
