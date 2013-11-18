package com.tor.io;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * User: tor
 * Date: 18.11.13
 * Time: 16:44
 * http://samolisov.blogspot.com/2013/11/java.html
 */
public class BlockedMultithreadingSiteReader {
    private static final int REQUESTS = 1000;

    private static final int THREADS = 4;

    private static final String HOST = "192.168.10.181";

    private static final int PORT = 7001;

    private static final String PATH_FMT = "/DemoServlet/demo?reqno={0}";

    public static class CrowlerTask implements Callable<Void> {

        private boolean finish = false;

        private int taskno;

        public CrowlerTask(int taskno) {
            this.taskno = taskno;
        }

        private String formatRequest() {
            return MessageFormat.format(PATH_FMT, taskno);
        }

        @Override
        public Void call() throws Exception {
            Socket socket = new Socket(HOST, PORT);
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write("GET " + formatRequest() + " HTTP/1.0\r\nHost: " +
                    HOST + "\r\n\r\n");
            writer.flush();

            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            byte[] buff = new byte[socket.getReceiveBufferSize()];
            int read = 0;
            while ((read = in.read(buff)) != -1) {
                System.out.println("Reqno: " + taskno + ", Readed: " + read);
            }
            System.out.println("Reqno: " + taskno + ", Readed: -1");
            socket.close();

            long mathstart = System.currentTimeMillis();
            long prod = 1;
            for (long i = 0; i < 150000; i++) {
                prod *= Math.random() * 100;
            }
            long mathend = System.currentTimeMillis();
            System.out.println("Prod: " + prod + " time: " + (mathend - mathstart) + " ms.");

            finish = true;

            return null;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long start = System.currentTimeMillis();

        final Future<Void>[] futures = new Future[REQUESTS];
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < REQUESTS; i++) {
            futures[i] = executor.submit(new CrowlerTask(i));
        }

        for (int i = 0; i < REQUESTS; i++) {
            futures[i].get();
        }

        long end = System.currentTimeMillis();
        System.out.println("date: " + new Date() + ", time: " + (end - start) + " ms.");
        executor.shutdown();
    }
}
