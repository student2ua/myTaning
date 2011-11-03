package com.tor.pattens.visitor.simplePizza.pizzaImpl;

import com.tor.pattens.visitor.simplePizza.Pizza;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 16:54:03
 * To change this template use File | Settings | File Templates.
 */
public class PopJohn implements Pizza {
    private static final Logger log = Logger.getLogger(PopJohn.class);
    final String name = "PopJohn";

    public String order() {
        return name;
    }
}
