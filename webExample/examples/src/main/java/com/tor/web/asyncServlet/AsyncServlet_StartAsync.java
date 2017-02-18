package com.tor.web.asyncServlet;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Tor
 * Date: 18.02.17
 * Time: 18:31
 * Servlet 3.0 (JSR 315), Tomcat 6+
 * http://pro-java.ru/java-dlya-opytnyx/asinxronnye-servlety-java/
 * Спецификация Servlet 3.0 предоставила метод startAsync(), сделавший доступными асинхронные операции.
 * Метод onComplete класса Asynclistener выполняется только после завершения выполнения.
 */
@WebServlet(urlPatterns = {"/async"}, asyncSupported = true)
public class AsyncServlet_StartAsync extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        final AsyncContext asyncContext = req.startAsync();
        String data = null;

        asyncContext.addListener(new AsyncListener() {

            private String data;

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                AsyncContext asyncContext = event.getAsyncContext();
                asyncContext.getResponse().getWriter().println(getData());
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                // выполняется, только если истекает время ожидания
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                //выполняется, только если была получена ошибка
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                //выполняется при запуске асинхронного контекста
            }

            public String getData() {
                return data;
            }
        });

        new Thread() {
            @Override
            public void run() {
                asyncContext.complete();
            }
        }.start();

        res.getWriter().write("Results:");
        // Чтение данных из базы данных

        data = "Queried data...";

        // Переводим поток в режим ожидания на некоторое время...
    }

}
