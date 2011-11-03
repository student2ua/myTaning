package com.tor.reflection.arrays;

import org.apache.log4j.Logger;

import java.lang.reflect.Array;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.01.2009
 * Time: 15:51:36
 * To change this template use File | Settings | File Templates.
 */
public class CreateAndExpand {
    private static final Logger log = Logger.getLogger(CreateAndExpand.class);

    public static void main(String[] args) {
        String[] array1 = (String[]) Array.newInstance(String.class, 5);
        String[][] array2 = (String[][]) Array.newInstance(String[].class, 5);
        String[][] array3 = (String[][]) Array.newInstance(String.class, new int[]{5, 9});
        String[] array5 = (String[]) Array.newInstance(array1.getClass().getComponentType(), Array.getLength(array1) + 10);
        System.arraycopy(array1, 0, array5, 0, Array.getLength(array1));
        Array.set(array5, 0, new String("Hello!!!!"));
        System.out.println("String array value of [0]: " + Array.get(array5, 0));

    }
}
