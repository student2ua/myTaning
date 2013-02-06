package com.tor.pattens.observer.v1;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 19:58
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://ayp-sd.blogspot.com/2013/01/observer-design-pattern-in-java.html
 */
public class AbstractEventPublisher<P extends EventPublisher<P, L, E>, L extends EventListener<P, L, E>, E> implements EventPublisher<P, L, E> {
    List<L> list = new CopyOnWriteArrayList<L>();

    @Override
    public void addListener(L listener) {
        list.add(listener);
    }

    @Override
    public void removeListener(L listener) {
        list.remove(listener);
    }

    @Override
    public void clearListeners() {
        list.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void publishEvent(E event) {
        for (L l : list) {
            l.handleEvent((P) this, event);
        }
    }
}
