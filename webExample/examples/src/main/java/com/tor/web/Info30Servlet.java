package com.tor.web;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * User: tor
 * Date: 12.07.19
 * Time: 19:56
 * ok
 */
@WebServlet("/info30")
public class Info30Servlet extends GenericServlet {

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        ServletConfig sc = getServletConfig();
//        sc.getServletContext()
        ServletContext sx = getServletContext();
        servletResponse.setContentType("text/html; charset=utf-8");
        PrintWriter pw = servletResponse.getWriter();
        pw.println("<html><head><title>Params Servlet</title></head>");
        pw.println("<body><h2>Сведения о сервлете</h2>");
        pw.println("name - " + sc.getServletName() + "<br>");
        pw.println("<h3>initParameterNames:</h3>");
        Enumeration<String> initParameterNames = sc.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String name = initParameterNames.nextElement();
            pw.print(name);
            pw.print("\t");
            pw.print(sc.getInitParameter(name));
            pw.println("<br>");
        }
        pw.println("<h2>ServletContext: </h2>");
        pw.println("ServerInfo - " + sx.getServerInfo() + "<br>");
        pw.println("ContextPath - " + sx.getContextPath() + "<br>");
        pw.println("ServletContextName - " + sx.getServletContextName() + "<br>");
        pw.println("MajorVersion MinorVersion - " + sx.getMajorVersion()+" "+sx.getMinorVersion() + "<br>");
        pw.println("Effective MajorVersion&MinorVersion - " +   sx.getEffectiveMajorVersion()+" "+sx.getEffectiveMinorVersion() + "<br>");
        pw.println("<h3>initParameterNames:</h3>");
        Enumeration<String> initParameterNamesSX = sx.getInitParameterNames();
        while (initParameterNamesSX.hasMoreElements()){
            String name=initParameterNamesSX.nextElement();
            pw.print(name);
            pw.print("\t");
            pw.print(sx.getInitParameter(name));
            pw.println("</br>");
        }
        pw.println("<h3>attributeNames:</h3>");
        Enumeration<String> attributeNames = sx.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String name=attributeNames.nextElement();
            pw.print(name);
            pw.print("\t");
            pw.print(sx.getInitParameter(name));
            pw.println("</br>");
        }
        pw.println("</body></html>");
        pw.flush();
        pw.close();
    }
}
