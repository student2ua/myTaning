package com.tor.pattens.visitor.CarElement.CarElementImpl;

import com.tor.pattens.visitor.CarElement.CarElement;
import com.tor.pattens.visitor.CarElement.CarElementVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.04.2010
 * Time: 17:49:09
 * To change this template use File | Settings | File Templates.
 */
public class Wheel implements CarElement {
    private String name;

    public Wheel(String name) {
        this.name = name;
    }

    public void assept(CarElementVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }
}
