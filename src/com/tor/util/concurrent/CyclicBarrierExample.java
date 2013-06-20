package com.tor.util.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 08.05.13
 * Time: 13:05
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class CyclicBarrierExample {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("All in barrier");
            }
        });
        Thread thread1 = new Thread(new Task(barrier));
        Thread thread2 = new Thread(new Task(barrier));
        Thread thread3 = new Thread(new Task(barrier));
        thread1.start();
        thread2.start();
        thread3.start();
    }

    private static class Task implements Runnable {
        private CyclicBarrier barrier;

        private Task(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting on barrier");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
