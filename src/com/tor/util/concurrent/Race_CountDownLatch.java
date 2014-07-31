package com.tor.util.concurrent;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * User: tor
 * Date: 31.07.14
 * Time: 13:52
 * CountDownLatch ����� ����������� ��� ������ �� ��� ���, ���� �� ����� ��������� ������������ �������. ��� ���������� �������
 * �� ����������� ��� ������ ������������
 * ��-������, ���� ������� ����������� ��� ������ ������������, �������� ����� ������, ������ ������� ������ �������
 * ��������� ��������� �����
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

                        System.out.println(horse + " �������� ��������� �������");
                        start.await();

                        int treveled = 0;
                        while (treveled < distance) {
                            // ����� 0-2 �������....
                            Thread.sleep(rnd.nextInt(3) * 1000);
                            // ... ������ �������� ��������� 0-14 �������
                            treveled += rnd.nextInt(15);
                            System.out.println(horse +                                     " advanced to " + treveled + "!");
                        }
                        finish.countDown();
                        System.out.println(horse + " �������� � ������!");
                        places.add(horse);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        System.out.println("� ... ������ ����������!");
        start.countDown();
        finish.await();
        System.out.println("------------------------");
        System.out.println("��������� �����������!");
        System.out.println(places.get(0) + " ���������� ������...");
        System.out.println(places.get(1) + " �������� �������...");
        System.out.println("� " + places.get(2) + " �������� ����� � �������.");

    }

    public static void main(String[] args)
            throws InterruptedException, java.io.IOException {
        System.out.println("Prepping...");

        Race_CountDownLatch r = new Race_CountDownLatch(
                "������� ��������",
                "���������� ���",
                "�����",
                "�������",
                "����",
                "�����",
                "�����"
        );

        System.out.println("��������� ������ " + r.distance + " ��������");

        System.out.println("Press Enter ....");
        System.in.read();

        r.run();
    }
}
