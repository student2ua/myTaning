package com.tor.pattens.visitor.simplePizza.visitorImpl;

import com.tor.pattens.visitor.simplePizza.Pizza;
import com.tor.pattens.visitor.simplePizza.Visitor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:08:05
 * To change this template use File | Settings | File Templates.
 */
public class ByPickup implements Visitor {
    private static final Logger log = Logger.getLogger(ByPickup.class);
    private String name;
    private final String method = "By pick up";

    public void visit(Pizza p) {
        name = p.order();
    }

    public String toString() {
        return name + " " + method;
    }
}
