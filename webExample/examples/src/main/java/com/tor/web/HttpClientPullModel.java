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
 * Time: 15:19
 * http://java.happycodings.com/servlets/code25.html
 * Here we have a client reading a book, specifying a
 * refresh every two seconds.  The current page is
 * stored in the client's HttpSession.
 *
 * When we have finished the book, we redirect the client
 * to the acknowledge web site
 *
 * Client Pull sees the client pulling content from the
 * server using the Refresh HTTP header directive
 */
public class HttpClientPullModel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse rez) throws ServletException, IOException {
        rez.setContentType("text/html");
        PrintWriter pw = rez.getWriter();
        // get a session to hold the current page
        HttpSession session = req.getSession();
        Integer pageN = (Integer) session.getAttribute("page");
        if (pageN == null) {
            pageN = 1;
        } else {
            pageN = pageN + 1;
        }
        pw.print("Page: " + pageN);
        // finish the book when its gets to page 10
        if (pageN.intValue() == 10) {
            pw.println("последняя страница. THE END");
            session.removeAttribute("page");
            session.invalidate();
            // finally, send the client to the client site after 5 secs
            rez.setHeader("Refresh", "5; URL=http://www.example.com");
        } else {
            rez.setHeader("Refresh", "2");
            session.setAttribute("page", pageN);
        }
        pw.println("<p><hr><p>");
        pw.println(new Date(System.currentTimeMillis()));
    }
}
