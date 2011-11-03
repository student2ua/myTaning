package com.tor.util.weakhash;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 07.06.2010
 * Time: 20:30:25
 * To change this template use File | Settings | File Templates.
 */
public class WeakTest {
    private static Map map;

    public static void main(String args[]) {
        map = new WeakHashMap();
        map.put(new String("Scott"), "McNealey");
        Runnable runner = new Runnable() {
            public void run() {
                while (map.containsKey("Scott")) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                    System.out.println("Checking for empty");
                    System.gc();
                }
            }
        };

        Thread t = new Thread(runner);
        t.start();
        System.out.println("Main joining");
        try {
            t.join();
        } catch (InterruptedException ignored) {
        }
    }
}
