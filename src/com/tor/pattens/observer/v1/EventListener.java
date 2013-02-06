package com.tor.pattens.observer.v1;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 19:46
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * <p/>
 * Observer
 */
public interface EventListener<P extends EventPublisher<P, L, E>, L extends EventListener<P, L, E>, E> {

    void handleEvent(P sender, E event);
}
