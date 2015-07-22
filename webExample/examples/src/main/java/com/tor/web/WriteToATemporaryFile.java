package com.tor.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: tor
 * Date: 06.07.15
 * Time: 18:42
 * http://java.happycodings.com/servlets/code56.html
 */
public class WriteToATemporaryFile extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // get the web applications temporary directory
        File tempDir = (File) getServletContext().
                getAttribute("javax.servlet.context.tempdir");

        // create a temporary file in that directory
        File tempFile = File.createTempFile(getServletName(), ".tmp", tempDir);

        // write to file
        FileWriter fw = new FileWriter(tempFile);
        try {
            fw.write("done and dusted");
        } finally {
            fw.close();
        }

        // tell servlet client where to look for file
        res.getWriter().println("check file: " + tempFile.getAbsolutePath());
    }

}