package com.tor.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * ����������� ������ ����� �������� ������
 */
public class CopyFileDemo {

    public static void main(String args[])
            throws IOException {
// ��������� ���������� ���������
        if (args.length != 2) {
            System.err.println("missing filenames");
            System.exit(1);
        }
// ������� � �������������� ������
        FileInputStream fis = new FileInputStream(args[0]);
        FileOutputStream fos = new FileOutputStream(args[1]);
        FileChannel fcin = fis.getChannel();
        FileChannel fcout = fos.getChannel();
// ��������� ����������� �����
        fcin.transferTo(0, fcin.size(), fcout);
// ���������
        fcin.close();
        fcout.close();
        fis.close();
        fos.close();
    }

    private static void writeContentToFile(String path, String content) throws Exception {
        FileOutputStream fos = null;
        FileLock lock = null;
        try {
            fos = new FileOutputStream(path);
            FileChannel fileChannel = fos.getChannel();
            lock = fileChannel.tryLock();
            if (lock != null) {
                fos.write(content.getBytes());
            }
        } finally {
            if (lock != null) {
                lock.release();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
