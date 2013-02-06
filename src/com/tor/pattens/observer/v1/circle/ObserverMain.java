package com.tor.pattens.observer.v1.circle;

import com.tor.pattens.observer.v1.circle.eventListener.SimpleCircleEventHandler;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 21:44
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://ayp-sd.blogspot.com/2013/01/observer-design-pattern-in-java.html
 */
public class ObserverMain {
    public static void main(String[] args) {
        Circle circle = new Circle(new Point(10, 10), 15, Color.RED);
        circle.addListener(new SimpleCircleEventHandler());

        circle.setCenter(new Point(5, 5));
        circle.setRadius(20);
        circle.setColor(Color.GREEN);
    }
}
