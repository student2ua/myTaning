package com.tor.pattens.bridge.v2;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 12.03.13
 * Time: 13:13
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://javapostsforlearning.blogspot.in/2012/09/bridge-design-pattern-in-java.html
 * Shape(Abstraction)
 */
public abstract class Shape {
    protected Colors color;

    public Shape(Colors color) {
        this.color = color;
    }

    abstract public void colorIt();

}
