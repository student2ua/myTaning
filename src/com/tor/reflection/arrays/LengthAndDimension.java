package com.tor.reflection.arrays;

import org.apache.log4j.Logger;

import java.lang.reflect.Array;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 14.01.2009
 * Time: 17:25:30
 * To change this template use File | Settings | File Templates.
 */
public class LengthAndDimension {
    private static final Logger log = Logger.getLogger(LengthAndDimension.class);

    public static void main(String[] args) {
        Object obj = new double[]{1, 2, 3, 4, 5, 6};
        boolean isArray = obj.getClass().isArray();
        System.out.println("Object is Array: " + isArray);
        if (isArray) {
            int length = Array.getLength(obj);
            System.out.println("Array length: " + length);

            int demension = getDimension(obj);
            System.out.println("demension = " + demension);
        }

    }
     //те вложенность
    private static int getDimension(Object obj) {
        int demension = 0;
        Class alf = obj.getClass();
        while (alf.isArray()) {
            alf = alf.getComponentType();
            demension++;
        }
        return demension;
    }
}
