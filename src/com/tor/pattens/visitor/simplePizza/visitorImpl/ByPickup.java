package com.tor.pattens.visitor.simplePizza.visitorImpl;

import com.tor.pattens.visitor.simplePizza.Pizza;
import com.tor.pattens.visitor.simplePizza.Visitor;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:08:05
 * взять пиццу с собой
 */
public class ByPickup implements Visitor {
    private String name;
    private final String method = "By pick up";

    public void visit(Pizza p) {
        name = p.order();
    }

    public String toString() {
        return name + " " + method;
    }
}
