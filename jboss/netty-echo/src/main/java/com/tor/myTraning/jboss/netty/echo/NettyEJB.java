package com.tor.myTraning.jboss.netty.echo;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created with IntelliJ IDEA.
 * User: tor
 * Date: 04.10.13
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
@Singleton(name = "NettyEJB")
@Startup
public class NettyEJB {
    @PostConstruct
    void init() {
        try {
            new NettyServer().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
