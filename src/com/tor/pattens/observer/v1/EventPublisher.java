package com.tor.pattens.observer.v1;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.02.13
 * Time: 19:50
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * <p/>
 * Observable
 */
public interface EventPublisher<P extends EventPublisher<P, L, E>, L extends EventListener<P, L, E>, E> {

    void addListener(L listener);

    void removeListener(L listener);

    void clearListeners();

    void publishEvent(E event);

}
