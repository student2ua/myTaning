package com.tor.util.concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * User: tor
 * Date: 31.07.14
 * Time: 13:32
 * В некоторых корпоративных системах разработчикам нередко нужно ограничивать количество открытых запросов
 * (потоков/действий), использующих определенный ресурс. На самом деле такое ограничение может иногда улучшить
 * производительность системы, снижая конкуренцию за данный ресурс. Конечно, можно попытаться вручную написать
 * осуществляющий регулировку код, однако легче использовать класс semaphore, который позаботится о регулировке за вас
 * Хотя в этом примере выполняется 10 потоков (в чем можно убедиться, выполнив для процесса, в котором работает SemApp,
 * команду jstack), только три из них являются активными. Остальные семь вынуждены ждать, пока какой-нибудь из выполняющихся
 * потоков не освободит семафор.
 * (В действительности класс Semaphore поддерживает захват и освобождение более чем одного разрешения за раз,
 * но в данном сценарии это не имеет смысла).
 */
public class SemaphoreApp {
    public static void main(String[] args) {
        Runnable limitedCall = new Runnable() {
            final Random rnd = new Random();
            final Semaphore availabe = new Semaphore(3);
            int count = 0;

            @Override
            public void run() {
                int time = rnd.nextInt(15);
                int num = count++;
                try {
                    availabe.acquire();
                    System.out.printf("# %d Start, time=%3d seconds%n", num, time);
                    Thread.sleep(time * 1000);
                    System.out.println("# " + num + " Done");
                    availabe.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(limitedCall).start();
        }
    }
}