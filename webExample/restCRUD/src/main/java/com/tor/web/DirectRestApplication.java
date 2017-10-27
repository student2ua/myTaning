package com.tor.web;

import com.tor.web.rest.IktElectronicCoursesEndpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * User: tor
 * Date: 20.10.17
 * Time: 22:05
 */
@ApplicationPath("/direct")
public class DirectRestApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public DirectRestApplication() {
        singletons.add(new IktElectronicCoursesEndpoint());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}