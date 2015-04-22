package com.tor.util.concurrent.exchanger;

import java.util.concurrent.Exchanger;

/**
 * User: tor
 * Date: 22.04.15
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class ExchangerRunnable implements Runnable {
    Exchanger exchanger = null;
    Object object = null;

    public ExchangerRunnable(Exchanger exchanger, Object object) {
        this.exchanger = exchanger;
        this.object = object;
    }

    @Override
    public void run() {
        try {
            Object previous = this.object;
            this.object = this.exchanger.exchange(this.object);
            System.out.println(Thread.currentThread().getName() + " exchanged = " + previous + " for " + this.object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
