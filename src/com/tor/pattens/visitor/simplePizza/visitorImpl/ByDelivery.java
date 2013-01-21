package com.tor.pattens.visitor.simplePizza.visitorImpl;

import com.tor.pattens.visitor.simplePizza.Pizza;
import com.tor.pattens.visitor.simplePizza.Visitor;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:13:28
 * пицца по доставке
 */
public class ByDelivery implements Visitor {
    String name;
    final String method = "By Delivery";

    public void visit(Pizza pizza) {
        name = pizza.order();
    }

    public String toString() {
        return name + " " + method;
    }

}
