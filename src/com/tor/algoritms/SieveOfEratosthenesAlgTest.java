package com.tor.algoritms;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang.time.StopWatch;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.04.2010
 * Time: 14:21:55
 * To change this template use File | Settings | File Templates.
 */
public class SieveOfEratosthenesAlgTest extends TestCase {
    //при 10 в 8 Out of Memory  я так понимаю в этом и западло TDPRIMES
    private static final int N = 1000000;

    public SieveOfEratosthenesAlgTest(String s) {
        super(s);
    }

    /**
     * Мы заводим массив на N элементов и заполняем его true. Затем последовательно проходим по нему до корня из N,
     * и встречая true, вычеркиваем все числа с этим шагом до N. Алгоритм работает за O(N*log(log(N))),
     * поэтому для небольших чисел он вполне подходит.
     */
    public void testDefAlgoritm() {
        boolean isOk = true;
        StopWatch watch = new StopWatch();
        watch.start();
        boolean[] result = new boolean[N];
        Arrays.fill(result, true);
        result[1] = false;
        for (int i = 2; i * i < N; i++) {
            if (result[i]) {
                for (int j = i * i; j < N; j = j + i) {
                    result[j] = false;
                }
            }
        }
        watch.stop();
        System.out.println("watch = " + watch);
        assertTrue(isOk);
    }

    public void testTDPRIMES() {
        boolean isOk = true;
        StopWatch watch = new StopWatch();
        watch.start();
        boolean[] result = new boolean[N];
        Arrays.fill(result, true);
        result[1] = false;
        for (int i = 2; i * i < N; i++) {
            if (result[i]) {
                for (int j = i * i; j < N; j = j + i) {
                    result[j] = false;
                }
            }
        }
        watch.stop();
        System.out.println("watch = " + watch);
        //TDPRIMES
        int iter = 100;
        for (int i = 2; i < N; i++) {
            if (result[i]) {
                if (iter == 100) {
                    System.out.println("i= " + i);
                    iter = 0;
                } else {
                    iter++;
                }
            }
        }
        assertTrue(isOk);
    }

    /**
     * Многим известно, что в нашем компьютере между процессорной и оперативной памятью находится кэш-память, работа
     * с ней проводится намного быстрее, чем с оперативной, но ее размеры ограничены. Например, при работе с большим
     * массивом, процессор загружает в кэш некоторую его часть, работает с ней, потом переносит обратно в оперативную,
     * загружает другую и так далее. А теперь вспомним наш алгоритм решета: каждое простое число мы вычеркивали из
     * всего массива, проходясь по нему от начала до конца. Поэтому процессор много раз будет загружать в кэш разные
     * отрезки массива и скорость на этом будет сильно теряться. Данный подход предлагает минимизировать затраты на
     * копирование массива из одной памяти в другую. Это сделать несложно если весь наш промежуток разделить на кусочки,
     * до 3*10^4 элементов, что приблизительно равно размеру кэша и работать с ними по-порядку.
     */
    public void testCashAndBitsPack() {
        boolean isOk = true;
        /**30000 0:00:01.875 - 0:00:01.625
         * 60000 0:00:01.813-0:00:01.625
         * 10000 0:00:01.797-0:00:01.672
         * 10000 0:00:01.844-0:00:01.594*/
        int CACHE =10000 ; // размер кэша
        int M = (int) Math.sqrt(N) + 1;
        int n = (int) Math.pow(10, 7);
        int m = n - N + 1;
        System.out.println("n = " + n);
        System.out.println("m = " + m);
        StopWatch watch = new StopWatch();
        watch.start();

        boolean[] result = new boolean[n];
        Arrays.fill(result, true);
        result[1] = false;
        for (int i = 2; i * i < n; i++) {
            if (result[i]) {
                for (int j = i * i; j < n; j = j + i) {
                    result[j] = false;
                }
            }
        }
        int[] primes = new int[(int) Math.sqrt(n)]; // заполняем его простыми до корня из n
        int iterPr = 1;
        for (int i = 1; i * i < n; i++) {
            if (result[i]) {
                primes[iterPr++] = i;
                //     System.out.println(iterPr - 1 + " i = " + i);
            }
        }

        //  int[] primes = new int[P]; // массив простых чисел до корня из N
        boolean[] segment = new boolean[CACHE]; // вторичное решето
        for (int I = M - 1; I < N; I += CACHE) {
            Arrays.fill(segment, true);
            for (int i = 1; i < iterPr; i++) {
                int h = I % primes[i];
                int j = h > 0 ? primes[i] - h : 0;
                for (; j < CACHE; j += primes[i])
                    segment[j] = false;
            }
            for (int i = 0; i < CACHE; i++) {
                if (segment[i] && (i + I < N)) {
                  //  System.out.println(i + I); // выводим простое число на экран
                }
            }
        }
        watch.stop();
        System.out.println("watch = " + watch);
        assertTrue(isOk);
    }

    /**
     * Рассмотрим случай, когда нам необходимо найти простые числа не от одного до N, а от n до m.
     * Пускай мы имеем ограничения 1 <= m <= n <= 10^9; n-m <= 10^6. Здесь нам необходимо применить алгоритм,
     * называемый двойным решетом. Он заключается в том чтобы найти простые числа до корня из n, затем сохранить их в
     * отдельный массив и точно так же «вычеркивать» эти простые числа с определенным шагом, но уже из необходимого нам
     * промежутка [m, n]. Кратко этот алгоритм будет выглядеть так:
     */
    public void testPrimeGenerator() {
        boolean isOk = true;
        int n = (int) Math.pow(10, 7);
        int m = n - N + 1;
        System.out.println("n = " + n);
        System.out.println("m = " + m);
        StopWatch watch = new StopWatch();
        watch.start();

        boolean[] result = new boolean[n];
        Arrays.fill(result, true);
        result[1] = false;
        for (int i = 2; i * i < n; i++) {
            if (result[i]) {
                for (int j = i * i; j < n; j = j + i) {
                    result[j] = false;
                }
            }
        }
        int[] primes = new int[(int) Math.sqrt(n)]; // заполняем его простыми до корня из n
        int iterPr = 1;
        for (int i = 1; i * i < n; i++) {
            if (result[i]) {
                primes[iterPr++] = i;
                //     System.out.println(iterPr - 1 + " i = " + i);
            }
        }
        boolean[] sieve = new boolean[n - m + 1]; // вторичное решето
        Arrays.fill(sieve, true);
        for (int i = 1; i < iterPr; i++) {
            //   System.out.println(i +" "+primes[i]);
            int h = m % primes[i];
            int j = h == 0 ? 0 : primes[i] - h;
            for (; j <= n - m; j += primes[i])
                sieve[j] = false;
        }
        watch.stop();
        System.out.println("watch = " + watch);

        assertTrue(isOk);
    }

    public static Test suite
            () {
        TestSuite suite = new TestSuite();
        //  suite.addTest(new SieveOfEratosthenesAlgTest("testDefAlgoritm"));
        //suite.addTest(new SieveOfEratosthenesAlgTest("testTDPRIMES"));
        suite.addTest(new SieveOfEratosthenesAlgTest("testPrimeGenerator"));
        suite.addTest(new SieveOfEratosthenesAlgTest("testCashAndBitsPack"));
        return suite;
    }
}
