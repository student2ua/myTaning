package com.tor.thread.pool.BRACKEEN_variant;

/*
DEVELOPING GAME IN JAVA

Caracteristiques

Editeur : NEW RIDERS
Auteur : BRACKEEN
Parution : 09 2003
Pages : 972
Isbn : 1-59273-005-1
Reliure : Paperback
Disponibilite : Disponible a la librairie
*/

import java.util.LinkedList;

public class ThreadPoolTest {

    public static void main(String[] args) {
       /* if (args.length != 2) {
            System.out.println("Tests the ThreadPool task.");
            System.out
                    .println("Usage: java ThreadPoolTest numTasks numThreads");
            System.out.println("  numTasks - integer: number of task to run.");
            System.out.println("  numThreads - integer: number of threads "
                    + "in the thread pool.");
            return;
        }
        int numTasks = Integer.parseInt(args[0]);
        int numThreads = Integer.parseInt(args[1]);*/
         int numTasks=50;
        int numThreads=10;
        // create the thread pool
        ThreadPool threadPool = new ThreadPool(numThreads);

        // run example tasks
        for (int i = 0; i < numTasks; i++) {
            threadPool.runTask(createTask(i));
        }

        // close the pool and wait for all tasks to finish.
        threadPool.join();
    }

    /**
     * Creates a simple Runnable that prints an ID, waits 500 milliseconds, then
     * prints the ID again.
     */
    private static Runnable createTask(final int taskID) {
        return new Runnable() {
            public void run() {
                System.out.println("Task " + taskID + ": start");

                // simulate a long-running task
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }

                System.out.println("Task " + taskID + ": end");
            }
        };
    }

}

/**
 * A thread pool is a group of a limited number of threads that are used to
 * execute tasks.
 */

class ThreadPool extends ThreadGroup {

    private boolean isAlive;

    private LinkedList taskQueue;

    private int threadID;

    private static int threadPoolID;

    /**
     * Creates a new ThreadPool.
     *
     * @param numThreads The number of threads in the pool.
     */
    public ThreadPool(int numThreads) {
        super("ThreadPool-" + (threadPoolID++));
        setDaemon(true);

        isAlive = true;

        taskQueue = new LinkedList();
        for (int i = 0; i < numThreads; i++) {
            new PooledThread().start();
        }
    }

    /**
     * Requests a new task to run. This method returns immediately, and the task
     * executes on the next available idle thread in this ThreadPool.
     * <p/>
     * Tasks start execution in the order they are received.
     *
     * @param task The task to run. If null, no action is taken.
     * @throws IllegalStateException if this ThreadPool is already closed.
     */
    public synchronized void runTask(Runnable task) {
        if (!isAlive) {
            throw new IllegalStateException();
        }
        if (task != null) {
            taskQueue.add(task);
            notify();
        }

    }

    protected synchronized Runnable getTask() throws InterruptedException {
        while (taskQueue.size() == 0) {
            if (!isAlive) {
                return null;
            }
            wait();
        }
        return (Runnable) taskQueue.removeFirst();
    }

    /**
     * Closes this ThreadPool and returns immediately. All threads are stopped,
     * and any waiting tasks are not executed. Once a ThreadPool is closed, no
     * more tasks can be run on this ThreadPool.
     */
    public synchronized void close() {
        if (isAlive) {
            isAlive = false;
            taskQueue.clear();
            interrupt();
        }
    }

    /**
     * Closes this ThreadPool and waits for all running threads to finish. Any
     * waiting tasks are executed.
     */
    public void join() {
        // notify all waiting threads that this ThreadPool is no
        // longer alive
        synchronized (this) {
            isAlive = false;
            notifyAll();
        }

        // wait for all threads to finish
        Thread[] threads = new Thread[activeCount()];
        int count = enumerate(threads);
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * A PooledThread is a Thread in a ThreadPool group, designed to run tasks
     * (Runnables).
     */
    private class PooledThread extends Thread {

        public PooledThread() {
            super(ThreadPool.this, "PooledThread-" + (threadID++));
        }

        public void run() {
            while (!isInterrupted()) {

                // get a task to run
                Runnable task = null;
                try {
                    task = getTask();
                } catch (InterruptedException ex) {
                }

                // if getTask() returned null or was interrupted,
                // close this thread by returning.
                if (task == null) {
                    return;
                }

                // run the task, and eat any exceptions it throws
                try {
                    task.run();
                } catch (Throwable t) {
                    uncaughtException(this, t);
                }
            }
        }
    }
}