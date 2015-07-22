package com.tor.web;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * User: tor
 * Date: 06.07.15
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class WorkingWithImages extends HttpServlet {
    private int counter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletOutputStream os = res.getOutputStream();
        res.setContentType("image/bmp");
        res.setContentLength(1234646874);
        URL resUrl = getServletContext().getResource("/img/asq.bmp");
        if (resUrl == null) {
            res.sendError(SC_NOT_FOUND);
            return;
        }
        URLConnection con = null;
        try {
            con = resUrl.openConnection();
            con.connect();
        } catch (IOException e) {
            res.sendError(SC_INTERNAL_SERVER_ERROR, "unable to read resource: /img/asq.bmp");
        }
        try {
            InputStream is = con.getInputStream();
            byte[] buf = new byte[8096];
            int byteRead;
            while ((byteRead = is.read(buf)) != -1) {
                os.write(buf, 0, byteRead);
            }
        } catch (IOException e) {
            res.sendError(SC_INTERNAL_SERVER_ERROR,"unable to send resource /img/asq.bmp");
        }
    }
}
