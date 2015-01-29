package com.tor.io;

import junit.framework.Assert;
import org.junit.Test;

import java.io.*;

/**
 * User: tor
 * Date: 12.12.13
 * Time: 15:00
 * http://www.javacodegeeks.com/2013/12/java-io-in-nutshell-22-case-studies.html#constant
 */
public class FileOperationTest {
    @Test
    public void testTwoConstantsInFile() throws Exception {
        System.out.println("File.separator " + File.separator);
        System.out.println("File.pathSeparator " + File.pathSeparator);
    }

    @Test
    public void testCreateAndDeleteFile() throws Exception {
        File file = new File("testFile.txt");
        Assert.assertTrue(file.createNewFile());

        if (file.exists()) {
            Assert.assertTrue(file.delete());
        } else {
            Assert.fail("file " + file + " not exist");
        }
    }

    @Test
    public void testListFilesAndDirectoriesInGivenDirectory() throws Exception {
        File file = new File(".");
        for (String s : file.list()) {
            System.out.println("s = " + s);
        }
        //or

        for (File file1 : file.listFiles()) {
            System.out.println("file1 = " + file1);
        }
    }

    /**
     * The following code creates two thread, a Producer which writes something into the pipeline at one end and a
     * Consumer which reads it from the pipeline at the other end. To create a pipeline, we need to create
     * PipedInputStream and PipedOutputStream seperately, and connect them using output.connect(input) or via their
     * constructors. In this program, I intentionally start the Consumer thread first and ask the whole program to
     * sleep 1 second before starting the Producer thread. This will show the pipeline DOES work. It is worthy noting
     * that, I close the pipeline in the Producer because “A thread that writes to a stream should always close the
     * OutputStream before terminating.
     */
    @Test
    public void testUsePipeline() throws Exception {
        PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();
        outputStream.connect(inputStream);//??

        new Thread(new Producer(outputStream)).start();
        Thread.sleep(1000);
        new Thread(new Consumer(inputStream)).start();
    }

    class Producer implements Runnable {
        private final OutputStream out;

        public Producer(OutputStream out) {
            this.out = out;
        }

        @Override
        public void run() {
            String str = "hello world!";
            try {
                out.write(str.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable {
        private final InputStream in;

        public Consumer(InputStream in) {
            this.in = in;
        }

        @Override
        public void run() {
            byte[] bs = new byte[1024];
            int len = -1;
            try {
                while ((len = in.read(bs)) != -1) {
                    System.out.println(new String(bs, 0, len));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testRedirectStandardIO() throws Exception {
        System.setOut(System.err);
    }

    @Test
    public void testPushBackBytes() throws Exception {
        StringBuilder builder = new StringBuilder();
        PushbackInputStream push = new PushbackInputStream(
                new ByteArrayInputStream("hello, world!".getBytes()));
        int temp = 0;
        while ((temp = push.read()) != -1) {
            if (temp == ',') {
                push.unread('.');
            }
            builder.append((char) temp);
        }
        Assert.assertEquals("hello,. world!", builder.toString());
        //-------------
        builder = new StringBuilder();
        PushbackInputStream push10 = new PushbackInputStream(
                new ByteArrayInputStream("hello, world!".getBytes()), 10);
        temp = 0;
        while ((temp = push10.read()) != -1) {
            if (temp == ',') {
                push10.unread("(...)".getBytes());
            }
            builder.append((char) temp);
        }
        Assert.assertEquals("hello,(...) world!", builder.toString());
    }
}
