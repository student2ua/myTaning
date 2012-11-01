package com.tor.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 08.08.12
 * Time: 21:14
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class NavigableSetDemo {
    public static void main(String[] args) {

        NavigableSet<String> navigableSet = new TreeSet<String>(Arrays.asList(
                "X", "B", "A", "Z", "T"));

        Iterator<String> iterator = navigableSet.descendingIterator();

        System.out.println("Original Set :");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        iterator = navigableSet.iterator();

        System.out.println("Sorted Navigable Set :");

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.printf("Head Set : %s.%n", navigableSet.headSet("X"));

        System.out.printf("Tail Set : %s.%n", navigableSet.tailSet("T", false));

        System.out.printf("Sub Set : %s.%n",
                navigableSet.subSet("B", true, "X", true));

        System.out.printf("Last Element : %s%n", navigableSet.last());

        System.out.printf("First Element : %s%n", navigableSet.first());

        System.out.printf("Reverse Set : %s%n", navigableSet.descendingSet());

        System.out.printf("Original Set : %s%n", navigableSet);

    }
}
