package com.tor.jms;


import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Admin
 * Date: 14.04.19
 * Time: 16:59
 */
public class ServiceServlet extends HttpServlet {
    @Inject
    Sender sender;

    private final String BR = "<br />";

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        String mode   = request.getParameter("mode"  );
        String prefix = request.getParameter("prefix");

        if(prefix == null || "".equals(prefix))
            prefix = "prefix";

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (mode.equalsIgnoreCase("send")) {
            out.print("send messages :" + BR);
            for (int i = 0; i < 3; i++) {
                String msg = prefix + " #" + i;
                out.print("&bull; " + msg + BR);
                sender.sendMessage(msg);
            }
            out.close();
        } else {
            out.print("receive messages :" + BR);
            if (Receiver.messages.size() > 0) {
                for (int i=0; i<Receiver.messages.size(); i++) {
                    out.print("&mdash; " +  Receiver.messages.get(i) + BR);
                }
                Receiver.messages.clear();
            }
            out.close();
        }}
}
