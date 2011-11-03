package com.tor.pattens.visitor.CarElement.CarElementVisitorImpl;

import com.tor.pattens.visitor.CarElement.CarElementImpl.Body;
import com.tor.pattens.visitor.CarElement.CarElementImpl.Car;
import com.tor.pattens.visitor.CarElement.CarElementImpl.EngineCar;
import com.tor.pattens.visitor.CarElement.CarElementImpl.Wheel;
import com.tor.pattens.visitor.CarElement.CarElementVisitor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.04.2010
 * Time: 17:59:16
 * To change this template use File | Settings | File Templates.
 */
public class CarElementDoVisitor implements CarElementVisitor {
    private static final Logger log = Logger.getLogger(CarElementDoVisitor.class);

    public void visit(Wheel wheel) {
        System.out.println("Kicking my " + wheel.getName() + " wheel");
    }

    public void visit(EngineCar engine) {
        System.out.println("Starting my engine");
    }

    public void visit(Body body) {
        System.out.println("Moving my body");
    }

    public void visit(Car car) {
        System.out.println("Starting my car");
    }
}
