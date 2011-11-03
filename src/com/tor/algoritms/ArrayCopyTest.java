package com.tor.algoritms;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 13:07:48
 * To change this template use File | Settings | File Templates.
 */
public class ArrayCopyTest extends TestCase {
    private static final Logger log = Logger.getLogger(ArrayCopyTest.class);
    int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int TEST_ITERATION = 10000000;

    public ArrayCopyTest(String s) {
        super(s);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ArrayCopyTest("testArrayCopyTest"));    //watch = 0:00:01.079
        suite.addTest(new ArrayCopyTest("testClone"));    //watch =   0:00:05.500
        suite.addTest(new ArrayCopyTest("testLoop"));    //watch =   0:00:00.563
        return suite;
    }

    public void testArrayCopyTest() {
        StopWatch watch = new StopWatch();
        watch.start();
        useArraycopy(a, TEST_ITERATION);
        watch.stop();
        System.out.println("watch = " + watch);
    }

    public void testClone() {
        StopWatch watch = new StopWatch();
        watch.start();
        useClone(a, TEST_ITERATION);
        watch.stop();
        System.out.println("watch = " + watch);
    }

    public void testLoop() {
        StopWatch watch = new StopWatch();
        watch.start();
        useLoop(a, TEST_ITERATION);
        watch.stop();
        System.out.println("watch = " + watch);
    }


    static void useArraycopy(int[] a, int n) {
        int[] copy = new int[a.length];
        for (int i = 0; i < n; i++)
            System.arraycopy(a, 0, copy, 0, a.length);
    }

    static void useClone(int[] a, int n) {
        int[] copy;
        for (int i = 0; i < n; i++)
            copy = (int[]) a.clone();
    }

/*  jdk 1.6
    static void useCopyOf(int[] a, int n) {
        int[] copy;
        for (int i=0; i<n; i++)
            copy = Arrays.copyOf(a,a.length);
    }
*/

    static void useLoop(int[] a, int n) {
        int[] copy = new int[a.length];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < a.length; j++)
                copy[j] = a[j];
    }
}
