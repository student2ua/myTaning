package com.tor.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * User: tor
 * Date: 06.07.15
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class GetServletSessionTimeInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        pw.println("<html><body>");

        HttpSession session = req.getSession();
        pw.println("session.getId() = " + session.getId());
        pw.println("session.getMaxInactiveInterval() = " + session.getMaxInactiveInterval());

        long dateCreation = session.getCreationTime();
        pw.println("<br> Sesion creatioon time " + new Date(dateCreation));

        dateCreation = session.getLastAccessedTime();
        if (dateCreation < 0) {
            pw.println("<BR>Session Last Access Time: Never Been Access Befored");
        } else {
            pw.println("session.getLastAccessedTime() = " + new Date(dateCreation));
        }
    }
}
