package com.tor.thread.pool;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 17.09.2009
 * Time: 14:33:21
 * To change this template use File | Settings | File Templates.
 */
public class ExampleTP {
    private static final Logger log = Logger.getLogger(ExampleTP.class);

    public static void main(String[] args) {
        {
           /*   ThreadPool2 pool2 = new ThreadPool2(5);
            for (int i = 1; i < 25; i++)
                //   ThreadPool.getInstance().join(ThreadPool2.getInstance().start(new TestWorkerThread()));
                //  pool2.join(pool2.start(new TestWorkerThread()));
            {
              pool2.start(new TestWorkerThread());
             //   pool2.start(new Ball());
                           System.out.println("i = " + i);
            }
            System.out.println("Все потоки запущены");
            ThreadPool2.getInstance().close();*/
            ThreadPool pool = new ThreadPool(10);

            for (int i = 1; i < 25; i++) {
                pool.assign(new TestWorkerThread());
            }

            pool.complete();

            System.out.println("All tasks are done.");
        }

    }
}
