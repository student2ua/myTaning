package com.tor.util.concurrent;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.*;

/**
 * User: tor
 * Date: 14.04.15
 * Time: 14:16
 * http://www.javacodegeeks.com/2013/11/throttling-task-submission-with-a-blockingexecutor-2.html
 * An executor which blocks and prevents further tasks from being submitted to the pool when the queue is full.
 */
public class BlockingExecutor extends ThreadPoolExecutor {
    private static final Logger LOGGER = Logger.getLogger(BlockingExecutor.class);
    private final Semaphore semaphore;

    public BlockingExecutor(int corePoolSize, int maximumPoolSize) {
        super(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        semaphore = new Semaphore(corePoolSize + maximumPoolSize);
    }

    @Override
    public void execute(final Runnable command) {
        boolean acquired = false;
        do {

            try {
                semaphore.acquire();
                acquired = true;
            } catch (InterruptedException e) {
                LOGGER.warn("InterruptedException whilst aquiring semaphore", e);
            }
        } while (!acquired);

        try {
            super.execute(command);
        } catch (final RejectedExecutionException e) {
            semaphore.release();
            throw e;
        }
    }

    @Override
    protected void afterExecute(final Runnable r, final Throwable t) {
        super.afterExecute(r, t);
        semaphore.release();
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new BlockingExecutor(2, 5);
        for (int i = 0; i < 10; i++) {
            final int num = i;
            executor.execute(new Runnable() {
                public Random rnd = new Random();

                @Override
                public void run() {
                    int time = rnd.nextInt(15);
                    try {
                        System.out.printf("# %d Start, time=%3d seconds%n", num, time);
                        Thread.sleep(time * 1000);
                        System.out.println("# " + num + " Done");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executor.shutdown();
    }
}
