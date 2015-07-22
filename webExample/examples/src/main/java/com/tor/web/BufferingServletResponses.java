package com.tor.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: tor
 * Date: 06.07.15
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class BufferingServletResponses extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        res.setBufferSize(32 * 1024);
        pw.println("111111111111111111111111111111");
        pw.println("222222222222222222222222222222");
        pw.println("333333333333333333333333333333");
        if (res.isCommitted()) {
            pw.println("<br> ok");
        } else {
            // clear the buffer, so what has gone before is lost
            res.resetBuffer();
            pw.println("clear buffer");
            // clears buffer, status codes and headers
            res.reset();
            res.setContentType("text/html");
            pw.println( "<br>but we are going to see this" );
        }

        res.flushBuffer();
        pw.println( "<br>and this will get flushed at the end of the method" );
        pw.println( "<br>current response buffer size is: " + res.getBufferSize());
    }
}
