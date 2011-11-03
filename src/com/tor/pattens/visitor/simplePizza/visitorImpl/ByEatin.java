package com.tor.pattens.visitor.simplePizza.visitorImpl;

import com.tor.pattens.visitor.simplePizza.Pizza;
import com.tor.pattens.visitor.simplePizza.Visitor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:09:49
 * To change this template use File | Settings | File Templates.
 */
public class ByEatin implements Visitor {
    private static final Logger log = Logger.getLogger(ByEatin.class);
    String name;
    final String method = "By Eat in";

    public void visit(Pizza pizza) {
        name = pizza.order();
    }

    public String toString() {
        return " name= " + name + ", metod= " + method;
    }
}
