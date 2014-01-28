package com.tor.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * User: tor
 * Date: 16.12.13
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
@ApplicationPath("/services")
public class UniverseApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public UniverseApplication() {
        singletons.add(new StudentResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
