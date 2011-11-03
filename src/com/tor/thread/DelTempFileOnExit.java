package com.tor.thread;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.12.2009
 * Time: 17:18:44
 * Пример удаления файла или директории при выходе из приложения
 */
public class DelTempFileOnExit {
    private static final Logger log = Logger.getLogger(DelTempFileOnExit.class);
    private static final String fileName = "file.loc";

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                // A File object to represent the filename
                File f = new File(fileName);
                // Make sure the file or directory exists and isn't write protected
                if (!f.exists()) throw new IllegalArgumentException("Delete: no such file or directory: " + fileName);

                if (!f.canWrite()) throw new IllegalArgumentException("Delete: write protected: " + fileName);

                // If it is a directory, make sure it is empty
                if (f.isDirectory()) {
                    String[] files = f.list();
                    if (files.length > 0)
                        throw new IllegalArgumentException(
                                "Delete: directory not empty: " + fileName);
                }
                // Attempt to delete it
                boolean success = f.delete();
                if (!success) throw new IllegalArgumentException("Delete: deletion failed");
            }
        }));
        try {
            new File(fileName).createNewFile();
        } catch (IOException e) {
            //e.printStackTrace();
            log.error(e);
        }
    }
}
