package com.tor.pattens.bridge.v2;

import com.tor.pattens.bridge.v2.colorsImp.BlueColors;
import com.tor.pattens.bridge.v2.colorsImp.RedColors;
import com.tor.pattens.bridge.v2.shareImp.CircleV2;
import com.tor.pattens.bridge.v2.shareImp.Rectangle;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 12.03.13
 * Time: 13:28
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://javapostsforlearning.blogspot.in/2012/09/bridge-design-pattern-in-java.html
 * <h2>Elements:</h2>
 * <h3>Abstraction</h3>
 * <ul>
 * <li>defines the abstraction' s interface.
 * <li>maintain reference to object of type implementor.
 * </ul>
 * <h3>RefinedAbstraction</h3>
 * extends the interface defined by abstraction.
 * <h3>Implementor</h3>
 * defines the interface for implementation classes.This interface doesn't have to correspond exactly to abstraction's interface;in fact the two interfaces can be quite different.Typically the implementor interface provides only primitive operations,and abstraction defines higher-level operations based on these primitives.
 * <h3>ConcreteImplementor</h3>
 * implements the implementor interface and defines its concrete implementation.
 * <h2>When to use it:</h2>
 * <ul>
 * <li>Abstraction and implementation should be bound at compile time.
 * <li>Both abstraction and implementation can have different hierarchies independently.You want to extend both hierarchies by subclassing.
 * <li>Changes in implementation should have no impact on abstraction.
 * <li>If you want to hide implementation of abstraction from client.
 * <li>Best use when you have multiple implementation.
 * </ul>
 * Read more at http://javapostsforlearning.blogspot.com/2012/09/bridge-design-pattern-in-java.html#kzlo2RUPSUsPHJgz.99
 * <h2>Related patterns:</h2>
 * An Abstract Factory can create and configure a particular bridge.
 */
public class BridgeDesignPatternMain {
    public static void main(String[] args) {
        Shape shape1 = new Rectangle(new RedColors());
        shape1.colorIt();
        Shape shape2 = new CircleV2(new BlueColors());
        shape2.colorIt();
    }
}
