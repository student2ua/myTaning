package com.tor.web.minimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Admin
 * Date: 17.01.15
 * Time: 18:55
 * http://devcolibri.com/4284
 */
@WebServlet("/")
public class WebServletMinimal extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name", "Admin");
        req.getRequestDispatcher("myjsp.jsp").forward(req, resp);
    }
}
