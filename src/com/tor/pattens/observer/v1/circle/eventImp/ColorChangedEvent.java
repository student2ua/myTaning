package com.tor.pattens.observer.v1.circle.eventImp;

import com.tor.pattens.observer.v1.circle.CircleEvent;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 21:17
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class ColorChangedEvent implements CircleEvent {
    private final Color oldColor;
    private final Color newColor;

    public ColorChangedEvent(Color oldColor, Color newColor) {
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    public Color getOldColor() {
        return oldColor;
    }

    public Color getNewColor() {
        return newColor;
    }
}
