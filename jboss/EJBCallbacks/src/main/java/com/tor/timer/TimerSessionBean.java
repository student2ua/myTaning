package com.tor.timer;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
@Stateless/*(name = "TimerSessionEJB")*/
public class TimerSessionBean implements TimerSessionBeanRemote {
    @Resource
    private SessionContext sessionContext;

    public TimerSessionBean() {
    }

    @Override
    public void createTimer(long duration) {
        sessionContext.getTimerService().createTimer(duration, "Hello World");
    }

    @Timeout
    public void timeOutHandler(Timer timer) {
        System.out.println("timer = " + timer.getInfo());
        timer.cancel();
    }
}
