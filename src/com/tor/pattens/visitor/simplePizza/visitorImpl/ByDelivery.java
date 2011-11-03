package com.tor.pattens.visitor.simplePizza.visitorImpl;

import com.tor.pattens.visitor.simplePizza.Pizza;
import com.tor.pattens.visitor.simplePizza.Visitor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:13:28
 * To change this template use File | Settings | File Templates.
 */
public class ByDelivery implements Visitor {
    private static final Logger log = Logger.getLogger(ByDelivery.class);
    String name;
    final String method = "By Delivery";

    public void visit(Pizza pizza) {
        name = pizza.order();
    }

    public String toString() {
        return name + " " + method;
    }

}
