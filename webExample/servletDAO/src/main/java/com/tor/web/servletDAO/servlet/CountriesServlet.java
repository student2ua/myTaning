package com.tor.web.servletDAO.servlet;

import com.tor.web.servletDAO.CountryDAO;
import com.tor.web.servletDAO.model.Country;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * User: tor
 * Date: 27.12.17
 * Time: 19:46
 * https://stackoverflow.com/tags/servlets/info
 * https://stackoverflow.com/questions/3541077/design-patterns-web-based-applications
 * todo Tomcat need add jstl-1.2.jar to /WEB-INF/lib
 */
@WebServlet("/countries")
public class CountriesServlet extends HttpServlet {
    // For Tomcat, define as <Resource> in context.xml and declare as <resource-ref> in web.xml
    @Resource(name="jdbc/dbOracle")
    private DataSource dataSource;
    private CountryDAO countryDAO;

    @Override
    public void init() throws ServletException {
        countryDAO = new CountryDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            List<Country> countries = countryDAO.findAll();
            // Will be available as ${countries} in JSP
            httpServletRequest.setAttribute("countries", countries);
            httpServletRequest.getRequestDispatcher("/WEB-INF/countries.jsp").forward(httpServletRequest, httpServletResponse);

        } catch (SQLException e) {
            throw new ServletException("Cannot obtain countries from DB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String delete = request.getParameter("delete");


        try {
            if (delete != null) { // Is the delete button pressed?
                countryDAO.delete(Integer.valueOf(delete));
            }
            String save = request.getParameter("save");

            if (save != null) { // Is the save button pressed? (note: if empty then no country ID was supplied, which means that it's "add country".
                Country country = (save.isEmpty()) ? new Country() : countryDAO.find(Integer.valueOf(save));
                country.setCountryName(request.getParameter("countryName"));
                String isCheck = request.getParameter("isCheck");
                country.setCheck(isCheck != null&&!isCheck.isEmpty() && Integer.parseInt(isCheck) == 0);
                String sortLevel = request.getParameter("sortLevel");
                country.setSortLevel(sortLevel != null&&!sortLevel.isEmpty()&&!sortLevel.equalsIgnoreCase("null") ? Integer.parseInt(sortLevel) : 0);
                country.setCapital(request.getParameter("capital"));
                country.setShortName(request.getParameter("shortName"));
                String codeIso = request.getParameter("codeIso");
                country.setCodeIso(codeIso!=null&&!codeIso.isEmpty()&& !codeIso.equalsIgnoreCase("null") ?Integer.parseInt(codeIso):0);
                countryDAO.save(country);
            }
            response.sendRedirect(request.getContextPath() + "/countries"); // Refresh page with table.
        } catch (Exception e) {
            throw new ServletException("Cannot obtain countries from DB", e);
        }
    }
}
