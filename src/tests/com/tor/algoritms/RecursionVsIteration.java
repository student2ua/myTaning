package com.tor.algoritms;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 30.03.2010
 * Time: 12:39:55
 * <p>Во-первых, рекурсия сама по себе не является алгоритмом, она является методом построения алгоритмов.
 * Ее очень удобно применять (но не обязательно эффективно) в тех случаях, когда можно выделить самоподобие задачи,
 * т.е. свести вычисление задачи некоторой размерности N к вычислению аналогичных задач меньшей размерности.</p>
 * <p>Во-вторых, если получается сделать алгоритм без применения рекурсии, то, скорее всего, им и надо воспользоваться.
 * Рекурсивные вызовы подпрограмм имеют свойство решать одну и ту же задачу бесчисленное количество раз
 * (во время повторов), что значительно сказывается на времени. Самым ярким примером является традиционное
 * вычисление чисел Фибоначчи рекурсивным и итеративным способами.</p>
 */
public class RecursionVsIteration extends TestCase {
    private static final Logger log = Logger.getLogger(RecursionVsIteration.class);
    private static final int N_FIBO = 40;

    public RecursionVsIteration(String s) {
        super(s);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new RecursionVsIteration("createFibonachiNumberUsingRecursion"));    //watch = 0:00:06.015
        suite.addTest(new RecursionVsIteration("createFibonachiNumberUsingIteration"));    //watch = 0:00:00.031
        return suite;
    }

    public void createFibonachiNumberUsingRecursion() {
        boolean isOk = true;
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            for (int n = 0; n < N_FIBO+1; n++) {
                System.out.println("n = " + n + " - " + recursionFibo(n));
            }
            watch.stop();
            System.out.println("watch = " + watch);
        } catch (Exception e) {
            e.printStackTrace();
            isOk = false;
        }
        assertTrue(isOk);
    }


    public void createFibonachiNumberUsingIteration() {
        boolean isOk = true;
        try {

            StopWatch watch = new StopWatch();
            watch.start();
            for (int n = 0; n < N_FIBO+1; n++) {
                System.out.println("n = " + n + " - " + iterationFibo(n));
            }
            watch.stop();
            System.out.println("watch = " + watch);
        } catch (Exception e) {
            e.printStackTrace();
            isOk = false;
        }
        assertTrue(isOk);
    }

    /**
     * Recursion Algoritm
     *
     * @param n Число для которого расчитываетсяя число Фибоначи
     * @return a number Fibonachi
     */
    private int recursionFibo(int n) {
        return n <= 1 ? n : recursionFibo(n - 1) + recursionFibo(n - 2);
    }

    /**
     * Iteration Algoritm
     * <p>храним где-то уже посчитанные значения (можно ограничиться только двумя последними) </p>
     *
     * @param n Число для которого расчитываетсяя число Фибоначи
     * @return a number Fibonachi
     */
    private int iterationFibo(int n) {
        int f_0 = 0;
        int f_1 = 1;
        int f_temp = 0;
        if (n < 1) return n;
        for (int k = 2; k < n; k++) {
            f_temp = f_0 + f_1;
            f_0 = f_1;
            f_1 = f_temp;
        }
        return f_temp;
    }
}
