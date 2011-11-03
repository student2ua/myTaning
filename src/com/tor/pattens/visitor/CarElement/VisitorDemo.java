package com.tor.pattens.visitor.CarElement;

import com.tor.pattens.visitor.CarElement.CarElementImpl.Car;
import com.tor.pattens.visitor.CarElement.CarElementVisitorImpl.CarElementDoVisitor;
import com.tor.pattens.visitor.CarElement.CarElementVisitorImpl.CarElementPrintVisitor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.04.2010
 * Time: 18:01:08
 * В объектно-ориентированном программировании паттерн Посетитель является способом разделения алгоритмов и объектных структур.
 */
public class VisitorDemo {
    private static final Logger log = Logger.getLogger(VisitorDemo.class);

    static public void main(String[] args) {
        Car car = new Car();
        car.assept(new CarElementPrintVisitor());
        car.assept(new CarElementDoVisitor());
    }
}
