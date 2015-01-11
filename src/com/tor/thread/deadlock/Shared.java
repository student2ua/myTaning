package com.tor.thread.deadlock;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 11.01.15
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public class Shared {
    synchronized void methodOne(Shared s) {
        Thread t = Thread.currentThread();

        System.out.println(t.getName() + " is executing method One...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getName() + " is calling method Two...");

        s.methodTwo(this);

        System.out.println(t.getName() + " is finished executing method One...");
    }

    synchronized void methodTwo(Shared s) {
        Thread t = Thread.currentThread();

        System.out.println(t.getName() + " is executing method Two...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getName() + " is calling methodOne...");

        s.methodOne(this);

        System.out.println(t.getName() + " is finished executing method Two...");
    }
}
