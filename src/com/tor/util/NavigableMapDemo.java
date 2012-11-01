package com.tor.util;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 08.08.12
 * Time: 21:10
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class NavigableMapDemo {
    public static void main(String[] args) {

        NavigableMap<String, Integer> navigableMap = new TreeMap<String, Integer>();

        navigableMap.put("X", 500);
        navigableMap.put("B", 600);
        navigableMap.put("A", 700);
        navigableMap.put("T", 800);
        navigableMap.put("Y", 900);
        navigableMap.put("Z", 200);

        System.out.printf("Descending Set  : %s%n", navigableMap.descendingKeySet());

        System.out.printf("Floor Entry  : %s%n", navigableMap.floorEntry("L"));

        System.out.printf("First Entry  : %s%n", navigableMap.firstEntry());

        System.out.printf("Last Key : %s%n", navigableMap.lastKey());

        System.out.printf("First Key : %s%n", navigableMap.firstKey());

        System.out.printf("Original Map : %s%n", navigableMap);

        System.out.printf("Reverse Map : %s%n", navigableMap.descendingMap());

    }

}
