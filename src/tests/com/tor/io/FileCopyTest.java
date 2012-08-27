package com.tor.io;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.07.12
 * Time: 13:32
 * копирование файла менее чем за 50мс, если более обрываем, не должно остаться мусора
 */
public class FileCopyTest extends TestCase {
// прерывание невозможно
    public static void testStreamCopy() {
        FileCopy fileCopy = new FileCopy() {
            @Override
            public void copy(String in, String out) throws IOException {

                FileInputStream fin = new FileInputStream(in);
                FileOutputStream fout = new FileOutputStream(out);

                byte[] buf = new byte[4096];
                int read;
                while ((read = fin.read(buf)) > -1) {
                    fout.write(buf, 0, read);
                }

                fin.close();
                fout.close();
            }
        };
    }

    //ok
    public static void testStreamCopyWithCheck4Interruption() {
        FileCopy fileCopy = new FileCopy() {
            @Override
            public void copy(String in, String out) throws IOException {

                FileInputStream fin = new FileInputStream(in);
                FileOutputStream fout = new FileOutputStream(out);

                byte[] buf = new byte[4096];
                int read;
                while ((read = fin.read(buf)) > -1) {
                    fout.write(buf, 0, read);
                    if (Thread.interrupted()) {
                        throw new IOException("Thread interrupted, cancelling");
                    }
                }
                fin.close();
                fout.close();
            }
        };
    }

    //unsafe
    public static void testNIOwithTransfer() {
        FileCopy fileCopy = new FileCopy() {
            @Override
            public void copy(String in, String out) throws IOException {

                FileChannel fin = new FileInputStream(in).getChannel();
                FileChannel fout = new FileOutputStream(out).getChannel();

                fout.transferFrom(fin, 0, new File(in).length());

                fin.close();
                fout.close();
            }
        };
    }

// ok
    public static void testNIOwithBuffering() {
        FileCopy fileCopy = new FileCopy() {
            @Override
            public void copy(String in, String out) throws IOException {

                FileChannel fin = new FileInputStream(in).getChannel();
                FileChannel fout = new FileOutputStream(out).getChannel();

                ByteBuffer buff = ByteBuffer.allocate(4096);
                while (fin.read(buff) != -1 || buff.position() > 0) {
                    buff.flip();
                    fout.write(buff);
                    buff.compact();
                }

                fin.close();
                fout.close();

            }
        };
    }

}
