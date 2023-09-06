package com.tor.csrf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: tor
 * Date: 030, 30.08.2021
 * Time: 12:52
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("tokenval");

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LoginServlet.CSRF_SESION_ATRR)) {
                    if (token != null && LoginServlet.hashAndSalt(token,LoginServlet.login.getBytes()).equals(cookie.getValue())) {
                        final PrintWriter writer = resp.getWriter();
                        writer.print("<script language='JavaScript'>alert('Success');</script>");
                    } else {
                        resp.getWriter().print("<script language='JavaScript'>alert('ERROR');</script>");
                    }
                }
            }
        }
    }
}
