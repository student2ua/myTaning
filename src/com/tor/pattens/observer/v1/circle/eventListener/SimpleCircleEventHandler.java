package com.tor.pattens.observer.v1.circle.eventListener;

import com.tor.pattens.observer.v1.circle.Circle;
import com.tor.pattens.observer.v1.circle.CircleEvent;
import com.tor.pattens.observer.v1.circle.CircleEventListener;
import com.tor.pattens.observer.v1.circle.eventImp.CenterChangedEvent;
import com.tor.pattens.observer.v1.circle.eventImp.ColorChangedEvent;
import com.tor.pattens.observer.v1.circle.eventImp.RadiusChangedEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 21:20
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class SimpleCircleEventHandler implements CircleEventListener {
    Map<Class<? extends CircleEvent>, CircleEventListener> handlers;

    public SimpleCircleEventHandler() {
        handlers = new HashMap<Class<? extends CircleEvent>, CircleEventListener>();

        handlers.put(CenterChangedEvent.class, new CenterChangedEventHandler());
        handlers.put(ColorChangedEvent.class, new ColorChangedEventHandler());
        handlers.put(RadiusChangedEvent.class, new RadiusChangedEventHandler());

    }

    @Override
    public void handleEvent(Circle sender, CircleEvent event) {
        CircleEventListener listener = handlers.get(event.getClass());
        if (listener != null) {
            listener.handleEvent(sender, event);
        }
    }

    private static class CenterChangedEventHandler implements CircleEventListener {

        @Override
        public void handleEvent(Circle sender, CircleEvent e) {
            CenterChangedEvent event = (CenterChangedEvent) e;
            String message = "center changed from %s to %s";
            System.out.println(String.format(message,
                    event.getOldCenter().toString(),
                    event.getNewCenter().toString()));
        }
    }

    private static class ColorChangedEventHandler implements CircleEventListener {

        @Override
        public void handleEvent(Circle sender, CircleEvent e) {
            ColorChangedEvent event = (ColorChangedEvent) e;
            String message = "color changed from %s to %s";
            System.out.println(String.format(message,
                    event.getOldColor().toString(),
                    event.getNewColor().toString()));
        }
    }

    private static class RadiusChangedEventHandler implements CircleEventListener {

        @Override
        public void handleEvent(Circle sender, CircleEvent e) {
            RadiusChangedEvent event = (RadiusChangedEvent) e;
            String message = "radius changed from %s to %s";
            System.out.println(String.format(message,
                    event.getOldRadius(),
                    event.getNewRadius()));
        }
    }
}
