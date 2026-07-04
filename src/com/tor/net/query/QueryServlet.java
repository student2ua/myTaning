package com.tor.net.query;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: tor
 * Date: 04.07.2026
 * Time: 15:50
 * 
 * <pre>
 * {@code
 * <servlet>
 *     <servlet-name>QueryServlet</servlet-name>
 *     <servlet-class>com.example.QueryServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 *     <servlet-name>QueryServlet</servlet-name>
 *     <url-pattern>/search</url-pattern>
 * </servlet-mapping>
 * }    </pre>
 */


public class QueryServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String method = req.getMethod();
        if (!"QUERY".equalsIgnoreCase(method)) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                          "Only QUERY method is supported");
            return;
        }

        String contentType = req.getContentType();
        if (contentType == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                          "Content-Type is required");
            return;
        }

        contentType = contentType.split(";")[0].trim(); // убираем charset

        StringBuilder body = new StringBuilder();
        BufferedReader reader = req.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line).append("\n");
            }
        } finally {
            reader.close();
        }

        String requestBody = body.toString().trim();

        System.out.println("QUERY received | Content-Type: " + contentType);
        System.out.println("Body: " + requestBody);

        Object result = null;

        try {
            if ("application/json".equals(contentType)) {
                result = handleJsonQuery(requestBody);
            }
            else if (isSqlLike(contentType, requestBody)) {
                result = handleSqlLikeQuery(requestBody);
            }
            else {
                resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                              "Supported Content-Types: application/json, text/plain (SQL-like)");
                return;
            }

            // Успешный ответ
        resp.setContentType("application/json; charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

            PrintWriter out = resp.getWriter();
            out.write("{\"success\": true, \"data\": " + result + "}");

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                          "Query processing error: " + e.getMessage());
        }
    }

    // ====================== Обработчики ======================

    private String handleJsonQuery(String body) {
        // Здесь можно использовать Gson или Jackson (для Java 6 — Gson проще)
        // Пример простой заглушки:
        return "{\"users\": [{\"id\":1,\"name\":\"Test\"}], \"count\": 42}";
    }

    private String handleSqlLikeQuery(String sql) {
        // Здесь вы можете:
        // 1. Проверить/санитизировать запрос (очень важно!)
        // 2. Передать в JDBC / ORM с ограничениями
        // 3. Запретить опасные команды (DROP, UPDATE, DELETE и т.д.)

        System.out.println("Executing SQL-like query: " + sql);

        // Пример заглушки
        return "{\"rows\": [{\"id\":1,\"name\":\"John\"}, {\"id\":2,\"name\":\"Jane\"}], \"count\": 2}";
    }

    private boolean isSqlLike(String contentType, String body) {
        if ("text/plain".equals(contentType) ||
            "application/sql".equals(contentType)) {
            return true;
        }
        // Дополнительно можно проверять, начинается ли тело с SELECT / WHERE и т.д.
        return body != null && body.trim().toUpperCase().startsWith("SELECT");
    }
}