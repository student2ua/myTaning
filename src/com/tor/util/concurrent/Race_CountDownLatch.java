package com.tor.util.concurrent;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * User: tor
 * Date: 31.07.14
 * Time: 13:52
 * CountDownLatch класс задерживает все потоки до тех пор, пока не будет выполнено определенное условие. При выполнении условия
 * он освобождает все потоки одновременно
 * Во-первых, одна защелка освобождает все потоки одновременно, имитируя старт скачек, однако позднее другая защелка
 * имитирует окончание гонки
 */
public class Race_CountDownLatch {
    private Random rnd = new Random();
    private int distance = rnd.nextInt(250);
    private List<String> horses = new ArrayList<String>();

    public Race_CountDownLatch(String... horsesName) {
        this.horses.addAll(Arrays.asList(horsesName));
    }

    public void run() throws InterruptedException {
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch finish = new CountDownLatch(horses.size());
        final List<String> places = Collections.synchronizedList(new ArrayList<String>());
        for (final String horse : horses) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        System.out.println(horse + " занимает стартовую позицию");
                        start.await();

                        int treveled = 0;
                        while (treveled < distance) {
                            // через 0-2 секунды....
                            Thread.sleep(rnd.nextInt(3) * 1000);
                            // ... лошадь проходит дистанцию 0-14 пунктов
                            treveled += rnd.nextInt(15);
                            System.out.println(horse +                                     " advanced to " + treveled + "!");
                        }
                        finish.countDown();
                        System.out.println(horse + " приходит к финишу!");
                        places.add(horse);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        System.out.println("И ... скачки Начинаются!");
        start.countDown();
        finish.await();
        System.out.println("------------------------");
        System.out.println("Обьявляем победителей!");
        System.out.println(places.get(0) + " завоёвывает Золото...");
        System.out.println(places.get(1) + " получает Серебро...");
        System.out.println("и " + places.get(2) + " вернется домой с Бронзой.");

    }

    public static void main(String[] args)
            throws InterruptedException, java.io.IOException {
        System.out.println("Prepping...");

        Race_CountDownLatch r = new Race_CountDownLatch(
                "Быстрый Гонзалес",
                "Неуловимый Джо",
                "Понни",
                "Карлсон",
                "Чума",
                "Голод",
                "Война"
        );

        System.out.println("Дистанция скачек " + r.distance + " попугаев");

        System.out.println("Press Enter ....");
        System.in.read();

        r.run();
    }
}
