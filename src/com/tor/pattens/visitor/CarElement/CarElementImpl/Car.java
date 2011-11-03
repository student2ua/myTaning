package com.tor.pattens.visitor.CarElement.CarElementImpl;

import com.agical.rmock.core.expectation.Engine;
import com.tor.pattens.visitor.CarElement.CarElement;
import com.tor.pattens.visitor.CarElement.CarElementVisitor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.04.2010
 * Time: 17:41:39
 * To change this template use File | Settings | File Templates.
 */
public class Car implements CarElement {
    private static final Logger log = Logger.getLogger(Car.class);
    CarElement[] elements;

    public Car() {
        this.elements = new CarElement[]
                {new Wheel("front left"), new Wheel("front right"),
                        new Wheel("back left"), new Wheel("back right"),
                        new Body(), new EngineCar()};

}

public Car(CarElement[]elements){
        this.elements=elements;
}

public CarElement[]getElements(){
        return(CarElement[])elements.clone();
}

public void assept(CarElementVisitor visitor){
        for(int it=0;it<this.getElements().length;it++){
        CarElement carElement=(CarElement)this.getElements()[it];
carElement.assept(visitor);
}
        visitor.visit(this);
}
        }
