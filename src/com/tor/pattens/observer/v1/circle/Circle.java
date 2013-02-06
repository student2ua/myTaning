package com.tor.pattens.observer.v1.circle;

import com.tor.pattens.observer.v1.AbstractEventPublisher;
import com.tor.pattens.observer.v1.circle.eventImp.CenterChangedEvent;
import com.tor.pattens.observer.v1.circle.eventImp.ColorChangedEvent;
import com.tor.pattens.observer.v1.circle.eventImp.RadiusChangedEvent;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 20:46
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://ayp-sd.blogspot.com/2013/01/observer-design-pattern-in-java.html
 */
public class Circle extends AbstractEventPublisher<Circle, CircleEventListener, CircleEvent> {
    private Point center;
    private Integer radius;
    private Color color;

    public Circle(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;

    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        if (!this.center.equals(center)) {
            publishEvent(new CenterChangedEvent(this.center, center));
        }
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        if (!this.radius.equals(radius)) {
            publishEvent(new RadiusChangedEvent(this.radius, radius));
        }
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (!this.color.equals(color)) {
            publishEvent(new ColorChangedEvent(this.color, color));
        }
        this.color = color;
    }
}
