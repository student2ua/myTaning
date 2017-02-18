package com.tor.web.asyncServlet;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
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
 * Date: 18.02.18
 * Time: 19:38
 * Servlet 3.1
 * javax.enterprise.concurrent.ManagedThreadFactory see pom file
 */
@WebServlet(urlPatterns = "/async/managedexecutorserviсе", asyncSupported = true)
public class AsyncServlet2_ManagedExecutorService extends HttpServlet {

    @Resource
    private ManagedExecutorService executor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        final AsyncContext asyncContext = req.startAsync();
        final PrintWriter writer = res.getWriter();
        executor.submit(new Runnable() {
            @Override
            public void run() {

                writer.println("Complete!");
                asyncContext.complete();

            }
        });
    }
}