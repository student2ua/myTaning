package com.tor.util.concurrent.exchanger;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

/**
 * User: tor
 * Date: 22.04.15
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
public class ExchangerCyclicBarrierMatrixSummator {
    private static int matrix[][] = {
            {1, 1, 1, 2},
            {2, 2, 2, 1}
    };
    private static int rezult[];

    public static void main(String[] args) {
        final int rows = matrix.length;
        rezult = new int[rows];
        final Exchanger<Integer> exchanger = new Exchanger<Integer>();
        Runnable mergerOnCBend = new Runnable() {
            @Override
            public void run() { // сложение сумм по строкам
                int sum = 0;
                for (int i = 0; i < rows; i++) {
                    sum += rezult[i];
                }
                try {
                    exchanger.exchange(sum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Сумма элементов матрицы равна: " + sum);
            }
        };
        CyclicBarrier barrier = new CyclicBarrier(rows, mergerOnCBend);
        // запуск всех сумматоров
        for (int i = 0; i < rows; i++) {
            new Summation(i, barrier).start();
        }
        System.out.println("Ожидание...");
        try {
            Integer rez = exchanger.exchange(-1);
            System.out.println("rez = " + rez);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Summation extends Thread {
        int row;
        CyclicBarrier barrier;

        private Summation(int row, CyclicBarrier barrier) {
            this.row = row;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int i : matrix[row]) {
                sum += i;
            }
            System.out.printf("Сумма элементов строки %d равна: %d%n", row, sum);
            rezult[row] = sum;
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
