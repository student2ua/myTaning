package com.tor.thread;


import java.util.concurrent.locks.LockSupport;

/**
 * User: tor
 * Date: 10.04.15
 * Time: 18:48
 * http://habrahabr.ru/post/171323/
 */
public class ThreadRing {
    private static int threadCount = 503;
    private static int n = 50000;

    public static void main(String[] args) throws InterruptedException {
        WorkThread[] threads = new WorkThread[threadCount];
        WorkThread first = new WorkThread(1);
        WorkThread next = first;
        for (int i = threadCount - 1; i > 0; i--) {
            WorkThread thread = new WorkThread(i + 1);
            threads[i] = thread;
            thread.next = next;
            thread.waiting = true;
            thread.start();
            next = thread;
        }
        threads[0] = first;
        first.next = next;
        first.message = n;
        first.waiting = false;
        first.start();
        for (WorkThread thread : threads) {
            thread.join();
        }

    }

    private static final class WorkThread extends Thread {
        private final int id;
        WorkThread next;
        volatile boolean waiting;
        int message;

        private WorkThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {

            int m;
            do {
                while (waiting) {
                    LockSupport.park();
                }
                m = message;
                waiting = true;
                next.message = m - 1;
                next.waiting = false;
                LockSupport.unpark(next);
            } while (m > 0);
            if (m == 0) System.out.println(id);
        }
    }
}
