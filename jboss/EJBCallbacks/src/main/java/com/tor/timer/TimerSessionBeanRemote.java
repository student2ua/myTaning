package com.tor.timer;

import javax.ejb.Remote;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
@Remote
public interface TimerSessionBeanRemote {
    void createTimer(long duration);
}
