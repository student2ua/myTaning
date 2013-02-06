package com.tor.pattens.observer.v1.circle.eventImp;

import com.tor.pattens.observer.v1.circle.CircleEvent;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 21:18
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class RadiusChangedEvent implements CircleEvent {
    private final int oldRadius;
    private final int newRadius;

    public RadiusChangedEvent(int oldRadius, int newRadius) {
        this.oldRadius = oldRadius;
        this.newRadius = newRadius;
    }

    public int getOldRadius() {
        return oldRadius;
    }

    public int getNewRadius() {
        return newRadius;
    }
}
