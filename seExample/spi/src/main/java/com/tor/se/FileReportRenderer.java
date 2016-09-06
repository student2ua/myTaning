package com.tor.se;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * User: tor
 * Date: 05.09.16
 * Time: 21:33
 * spi
 */
public class FileReportRenderer extends ReportRenderer {
    @Override
    public void generateReport() {
        final List<String> music = findMusic();
        try {
            FileWriter writer = new FileWriter("music.txt");
            for (String compozition : music) {
                writer
                        .append(compozition)
                        .append("\n");
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
