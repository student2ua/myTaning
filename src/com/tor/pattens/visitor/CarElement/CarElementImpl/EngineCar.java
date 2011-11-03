package com.tor.pattens.visitor.CarElement.CarElementImpl;

import org.apache.log4j.Logger;
import com.tor.pattens.visitor.CarElement.CarElement;
import com.tor.pattens.visitor.CarElement.CarElementVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.04.2010
 * Time: 17:47:54
 * To change this template use File | Settings | File Templates.
 */
public class EngineCar implements CarElement {
    private static final Logger log = Logger.getLogger(EngineCar.class);

    public void assept(CarElementVisitor visitor) {
        visitor.visit(this);
    }
}
