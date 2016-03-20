package com.tor.web.formParamMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Date: 20.03.16
 * Time: 8:36
 * http://theopentutorials.com/post/uncategorized/get-all-parameters-in-html-form-using-getparametermap/
 * The method getParameterMap() from javax.servlet.ServletRequest is used to get all form data.
 * Method signature:
 * <p/>
 * Map getParameterMap()
 * <p/>
 * Returns an immutable java.util.Map containing parameter names as keys and parameter values as map values.
 * The keys in the parameter map are of type String. The values in the parameter map are of type String array.
 * For Servlets, parameters are contained in the query string (GET request) or request body (POST request).
 * If the request has no parameters, the method returns an empty Map.
 */
public class FormParamMap extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest hreq, HttpServletResponse hresp) throws ServletException, IOException {
        final Map<String, String[]> map = hreq.getParameterMap();
        PrintWriter out = hresp.getWriter();
        hresp.setContentType(MediaType.TEXT_HTML);

        out.print("<html><body>");
        out.print("<h1> Your Order...</h1>");
        out.println("<table border=\"1\" cellpadding = \"5\"" + " cellspacing = \"5\">");
        out.println("<tr> <th>Parameter Name</th>" + "<th>Parameter Value</th></tr>");
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String paramName = entry.getKey();
            out.print("<tr><td>" + paramName + "</td><td>");
            String[] paramValues = entry.getValue();
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() == 0)
                    out.println("<b>No Value</b>");
                else
                    out.println(paramValue);
            } else {
                out.println("<ul>");
                for (int i = 0; i < paramValues.length; i++) {
                    out.println("<li>" + paramValues[i] + "</li>");
                }
                out.println("</ul>");
            }
            out.print("</td></tr>");
        }
        out.println("</table></body></html>");
    }
}
