package com.tor.web.asyncServlet;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Tor
 * Date: 18.02.17
 * Time: 19:07
 *  Servlet 3.1
 * javax.enterprise.concurrent.ManagedThreadFactory see pom file
 */
@WebServlet(urlPatterns = "/async/managedthreadfactory", asyncSupported = true)
public class AsyncServlet_ManagedThreadFactory extends HttpServlet {

    @Resource
    private javax.enterprise.concurrent.ManagedThreadFactory factory;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();
        final PrintWriter writer = res.getWriter();
        /** новый поток с процессом, требующим больших затрат времени, и наконец вызывает функцию complete
         * из asyncContext. ManagedThreadFactory служит в качестве доступного потока из пула, который вам необходимо
         * запустить явным образом.*/
        Thread thread = factory.newThread(new Runnable() {

            @Override
            public void run() {
                writer.println("Complete!");
                asyncContext.complete();
            }
        });
        thread.start();
    }
}