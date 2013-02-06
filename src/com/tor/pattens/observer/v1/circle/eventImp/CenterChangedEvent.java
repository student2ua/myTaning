package com.tor.pattens.observer.v1.circle.eventImp;

import com.tor.pattens.observer.v1.circle.CircleEvent;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 20:53
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class CenterChangedEvent implements CircleEvent {
    private Point oldCenter;
    private Point newCenter;

    public CenterChangedEvent(Point oldCenter, Point newCenter) {
        this.oldCenter = oldCenter;
        this.newCenter = newCenter;
    }

    public Point getOldCenter() {
        return oldCenter;
    }

    public void setOldCenter(Point oldCenter) {
        this.oldCenter = oldCenter;
    }

    public Point getNewCenter() {
        return newCenter;
    }

    public void setNewCenter(Point newCenter) {
        this.newCenter = newCenter;
    }
}
