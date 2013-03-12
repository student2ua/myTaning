package com.tor.pattens.bridge.v2.shareImp;

import com.tor.pattens.bridge.v2.Colors;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 12.03.13
 * Time: 13:17
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://javapostsforlearning.blogspot.in/2012/09/bridge-design-pattern-in-java.html
 * Rectangle(RefinedAbstraction)
 */
public class Rectangle extends com.tor.pattens.bridge.v2.Shape {
    public Rectangle(Colors color) {
        super(color);
    }

    @Override
    public void colorIt() {
        System.out.println("Rectangle color It");
        color.fillColor();
    }
}
