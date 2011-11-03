package com.tor.pattens.visitor.CarElement.CarElementImpl;

import com.tor.pattens.visitor.CarElement.CarElement;
import com.tor.pattens.visitor.CarElement.CarElementVisitor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.04.2010
 * Time: 17:39:09
 * To change this template use File | Settings | File Templates.
 */
public class Body implements CarElement {
    private static final Logger log = Logger.getLogger(Body.class);
    String name = "Body";

    public Body(String name) {
        this.name = name;
    }

    public Body() {

    }

    public String getName() {
        return name;
    }

    public void assept(CarElementVisitor visitor) {
        visitor.visit(this);
    }
}
