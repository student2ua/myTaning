package com.tor.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.07.12
 * Time: 13:28
 * http://www.javacodegeeks.com/2012/07/io-vs-nio-interruptions-timeouts-and.html
 */
public abstract class FileCopy {
    void testCopy() throws Exception {
     ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newCachedThreadPool();
     final long start = System.currentTimeMillis();
     Callable<Object> task = new Callable<Object>() {
      @Override
      public Object call() throws Exception {
       try {
        copy("a.bin", "b.bin");
       } catch (Exception e) {
        e.printStackTrace();
       }
       System.out.println("Call really finished after: "
         + (System.currentTimeMillis() - start));
       return null;
      }
     };
     Collection<Callable<Object>> taskWrapper = Arrays.asList(task);
     List<Future<Object>> futures = exec.invokeAll(taskWrapper, 50,
       TimeUnit.MILLISECONDS);
     System.out.println("invokeAll finished after: "
       + (System.currentTimeMillis() - start));
     System.out.println("Future.isCancelled? "
       + futures.get(0).isCancelled());
     Thread.sleep(20);
     System.out.println("Threads still active: " + exec.getActiveCount());
    }

    abstract public  void copy(String in, String out) throws Exception;
}
