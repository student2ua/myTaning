package com.tor.io;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.08.13
 * Time: 15:27
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 *
 * @since JDK 6
 */
public class SpaceChecker {
    public static void main(String[] args) {
        File[] roots = File.listRoots();

        for (int i = 0; i < roots.length; i++) {
            System.out.println(roots[i]);
            System.out.println("Free space = " + roots[i].getFreeSpace());
            System.out.println("Usable space = " + roots[i].getUsableSpace());
            System.out.println("Total space = " + roots[i].getTotalSpace());
            System.out.println();
        }
    }
}
