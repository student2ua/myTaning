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

@WebServlet("/country")
public class CountryServlet extends HttpServlet {
    // For Tomcat, define as <Resource> in context.xml and declare as <resource-ref> in web.xml
    @Resource(name = "jdbc/dbOracle")
    private DataSource dataSource;
    private CountryDAO countryDAO;

    @Override
    public void init() throws ServletException {
        countryDAO = new CountryDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String edit = request.getParameter("edit");

        try {
            if (edit != null) { // Is the edit link clicked?
                Country country = countryDAO.find(Integer.valueOf(edit));
                request.setAttribute("country", country); // Will be available as ${country} in JSP.
            }

            request.getRequestDispatcher("/WEB-INF/country.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain country from DB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String save = request.getParameter("save");


        try {
            if (save != null) { // Is the save button pressed? (note: if empty then no country ID was supplied, which means that it's "add country".
                Country country = (save.isEmpty()) ? new Country() : countryDAO.find(Integer.valueOf(save));
                country.setCountryName(request.getParameter("countryName"));
                String isCheck = request.getParameter("isCheck");
                boolean chek = false;
                if (isCheck != null && !isCheck.isEmpty()) {
                    if (isCheck.equalsIgnoreCase("true")) chek = true;
                    else if (isCheck.matches("-?\\d+(\\.\\d+)?")) chek = Integer.parseInt(isCheck) == 0;
                }
                country.setCheck(chek);
                String sortLevel = request.getParameter("sortLevel");
                country.setSortLevel(sortLevel != null ? Integer.parseInt(sortLevel) : 0);
                country.setCapital(request.getParameter("capital"));
                country.setShortName(request.getParameter("shortName"));
                String codeIso = request.getParameter("codeIso");
                country.setCodeIso(codeIso != null ? Integer.parseInt(codeIso) : 0);
                if (country.getCountryId()!=null) {
                    if (!countryDAO.update(country)) {
                        System.out.println(country+ "save Error");
                    }
                } else {
                    countryDAO.save(country);
                }

            }

            response.sendRedirect(request.getContextPath() + "/countries"); // Go to page with table.
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain country from DB", e);
        } catch (Exception e) {
            throw new ServletException("Cannot obtain country from DB", e);
        }
    }

}
