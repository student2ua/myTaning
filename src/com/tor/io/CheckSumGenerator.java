package com.tor.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 07.11.12
 * Time: 17:03
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * в действительности обрабатываем массив байт апдейтом
 */
public class CheckSumGenerator {
    /**
     * @param name_Algorithm in "SHA1","MD5" ets
     */
    public static byte[] generateByteHash(File file, String name_Algorithm) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(name_Algorithm);
        InputStream stream = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int nRead = 0;
        while ((nRead = stream.read(buf)) != -1) {
            messageDigest.update(buf, 0, nRead);

        }
        return messageDigest.digest();
    }

    /**
     * Adler32(zip) or CRC32
     */
    public static long generateAdler32(File file, String name) throws IOException {
        Checksum checksum;
        if (name.contains("Adler")) {
            checksum = new Adler32();
        } else {
            checksum = new CRC32();
        }
        CheckedInputStream checkedInputStream = new CheckedInputStream(new FileInputStream(file),checksum);
        byte[] tempBuf = new byte[128];
        while (checkedInputStream.read(tempBuf) >= 0) {
        }
        return checkedInputStream.getChecksum().getValue();
    }

    public static String byteToString(byte[] inB) {
        StringBuilder rez = new StringBuilder();
        for (byte b : inB) {
            rez.append(Integer.toString((b & 0xff), 16).substring(1));
        }

        return rez.toString();
    }
    public static void main(String[] args) throws IOException {
        System.out.println("E:/st1_r.png CRC32 " + CheckSumGenerator.generateAdler32(new File("E:\\st1_r.png"), ""));
    }
}
