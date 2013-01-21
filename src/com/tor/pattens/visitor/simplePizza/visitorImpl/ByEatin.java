package com.tor.pattens.visitor.simplePizza.visitorImpl;

import com.tor.pattens.visitor.simplePizza.Pizza;
import com.tor.pattens.visitor.simplePizza.Visitor;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:09:49
 * Слопать пицу на месте
 */
public class ByEatin implements Visitor {
    String name;
    final String method = "By Eat in";

    public void visit(Pizza pizza) {
        name = pizza.order();
    }

    public String toString() {
        return " name= " + name + ", metod= " + method;
    }
}
