package com.tor.pattens.visitor.CarElement;

import com.tor.pattens.visitor.CarElement.CarElementImpl.Body;
import com.tor.pattens.visitor.CarElement.CarElementImpl.Car;
import com.tor.pattens.visitor.CarElement.CarElementImpl.EngineCar;
import com.tor.pattens.visitor.CarElement.CarElementImpl.Wheel;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.04.2010
 * Time: 17:35:17
 * To change this template use File | Settings | File Templates.
 */
public interface CarElementVisitor {

    public void visit(Car car);

    public void visit(Body body);

    public void visit(EngineCar engineCar);

    public void visit(Wheel whill);
}
