package com.tor.util.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: tor
 * Date: 22.04.15
 * Time: 15:51
 */
public class LatchCondition {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private volatile boolean flag = false;

    public void waitTillChange() {
        lock.lock();
        try {
            while (!flag) {
                condition.await();
            }
        } catch (InterruptedException ignore) {
        } finally {
            lock.unlock();
        }
    }

    public void change() {
        lock.lock();
        try {
            flag = true;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
