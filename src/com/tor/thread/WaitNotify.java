package com.tor.thread;

/**
 * User: tor
 * Date: 22.04.15
 * Time: 15:33
 * вместо слипа можем постоянно опрашивать флаг
 */
public class WaitNotify {
    private
    final Object lock = new Object();
    private volatile boolean flag = false;

    public void waitTillChange() {
        synchronized (lock) {
            while (!flag) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

    }

    public void change() {
        synchronized (lock) {
            flag = true;
            lock.notifyAll();
        }
    }
}
