package com.tor.pattens.visitor.simplePizza.pizzaImpl;

import com.tor.pattens.visitor.simplePizza.Pizza;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:06:19
 * To change this template use File | Settings | File Templates.
 */
public class GodFather implements Pizza {
    private static final Logger log = Logger.getLogger(GodFather.class);
    final String name = "GodFather";

    public String order() {
        return name;
    }
}
