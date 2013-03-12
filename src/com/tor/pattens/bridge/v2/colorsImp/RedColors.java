package com.tor.pattens.bridge.v2.colorsImp;

import com.tor.pattens.bridge.v2.Colors;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 12.03.13
 * Time: 13:23
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://javapostsforlearning.blogspot.in/2012/09/bridge-design-pattern-in-java.html
 * (ConcreteImplementor)
 */
public class RedColors implements Colors {
    @Override
    public void fillColor() {
        System.out.println(" fill Red Color");
    }
}
