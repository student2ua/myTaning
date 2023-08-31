package com.tor.csrf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * User: tor
 * Date: 030, 30.08.2021
 * Time: 10:43
 */
@WebServlet("/logOut")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        Cookie loginCookie = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    loginCookie = cookie;
                    break;
                }
            }
        }
        if (loginCookie != null) {
            loginCookie.setMaxAge(0);
            resp.addCookie(loginCookie);
        }
        final HttpSession session = req.getSession();
        session.setAttribute(LoginServlet.CSRF_PARAM_NAME, null);
        resp.sendRedirect("login.jsp");
    }
}
