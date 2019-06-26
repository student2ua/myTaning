package com.tor.web.servletDAO;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/country")
public class CountryServlet extends HttpServlet {
    // For Tomcat, define as <Resource> in context.xml and declare as <resource-ref> in web.xml
    @Resource(name="jdbc/dbOracle")
    private DataSource dataSource;
    private CountryDAO countryDAO;

    @Override
    public void init() throws ServletException {
        countryDAO = new CountryDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String edit = request.getParameter("edit");

        try {
            if (edit != null) { // Is the edit link clicked?
                Country country = countryDAO.find(Integer.valueOf(edit));
                request.setCharacterEncoding("UTF-8");
                request.setAttribute("country", country); // Will be available as ${country} in JSP.
            }

            request.getRequestDispatcher("/WEB-INF/country.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain country from DB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String save = request.getParameter("save");


        try {
            if (save != null) { // Is the save button pressed? (note: if empty then no country ID was supplied, which means that it's "add country".
                Country country = (save.isEmpty()) ? new Country() : countryDAO.find(Integer.valueOf(save));
                country.setCountryName(request.getParameter("countryName"));
                country.setCheck(Integer.parseInt(request.getParameter("isCheck")) == 0);
                country.setSortLevel(Integer.parseInt(request.getParameter("sortLevel")));
                country.setCapital(request.getParameter("capital"));
                country.setShortName(request.getParameter("shortName"));
                country.setCodeIso(Integer.parseInt(request.getParameter("codeIso")));
                countryDAO.save(country);
            }

            response.sendRedirect(request.getContextPath() + "/countries"); // Go to page with table.
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain country from DB", e);
        }
    }

}
