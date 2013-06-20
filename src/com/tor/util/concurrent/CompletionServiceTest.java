package com.tor.util.concurrent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 11.06.13
 * Time: 20:03
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://rdafbn.blogspot.gr/2013/01/executorservice-vs-completionservice-vs.html
 */

public class CompletionServiceTest {

    private static final int waittime = 200;
    private static final int numberOfThreadsInThePool = 3;

    private final List<String> printRequests = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
    );

    void normalLoop() {
        for (String image : printRequests) {
            try {
                Thread.sleep(waittime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(image);
        }
    }

    void normalExecutorService() {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreadsInThePool);
        try {
            Set<Future<String>> printTaskFutures = new HashSet<Future<String>>();
            for (final String printRequest : printRequests) {
                printTaskFutures.add(executor.submit(new Printer(printRequest)));
            }
            for (Future<String> future : printTaskFutures) {
                System.out.print(future.get());

            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            if (executor != null) {
                executor.shutdownNow();
            }
        }
    }

    void completionService() {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreadsInThePool);
        CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
        for (final String printRequest : printRequests) {
            completionService.submit(new Printer(printRequest));
        }
        try {
            for (int t = 0, n = printRequests.size(); t < n; t++) {
                Future<String> f = completionService.take();
                System.out.print(f.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (executor != null) {
                executor.shutdownNow();
            }
        }

    }

    private class Printer implements Callable<String> {

        private final String toPrint;

        public Printer(String toPrint) {
            this.toPrint = toPrint;
        }

        public String call() {
            try {
                Thread.sleep(waittime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return toPrint;
        }
    }

    public static void main(String[] args) {
        System.out.println("Normal Executor Service");
        long start = System.currentTimeMillis();
        new CompletionServiceTest().normalExecutorService();
        System.out.println();
        System.out.println("Execution time : " + (System.currentTimeMillis() - start));

        System.out.println("Completion Service");
        start = System.currentTimeMillis();
        new CompletionServiceTest().completionService();
        System.out.println();
        System.out.println("Execution time : " + (System.currentTimeMillis() - start));

        System.out.println("Normal Loop");
        start = System.currentTimeMillis();
        new CompletionServiceTest().normalLoop();
        System.out.println();
        System.out.println("Execution time : " + (System.currentTimeMillis() - start));

    }
}