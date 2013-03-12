package com.tor.pattens.bridge.v2.shareImp;

import com.tor.pattens.bridge.v2.Colors;
import com.tor.pattens.bridge.v2.Shape;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 12.03.13
 * Time: 13:49
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://javapostsforlearning.blogspot.in/2012/09/bridge-design-pattern-in-java.html
 */
public class CircleV2 extends Shape {
    public CircleV2(Colors color) {
        super(color);
    }

    @Override
    public void colorIt() {
        System.out.println(" Circle color It");
        color.fillColor();
    }
}
