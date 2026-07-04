package com.tor.net.query;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: tor
 * Date: 04.07.2026
 * Time: 15:41
 * HTTP QUERY
 */
public class QueryExample {
/*    private void apachVariant(){
        // HttpClient 4.x
     DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            // Создаём кастомный запрос QUERY
            HttpEntityEnclosingRequestBase request = new HttpEntityEnclosingRequestBase() {
                @Override
                public String getMethod() {
                    return "QUERY";
                }
            };

            request.setURI(new URI("https://api.example.com/search"));

            // Заголовки
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            request.setHeader("Accept", "application/json");
            request.setHeader("User-Agent", "Java-HttpClient-QUERY");

            // Тело запроса
            String jsonBody = "{\n" +
                    "  \"query\": \"java httpclient\",\n" +
                    "  \"filters\": {\n" +
                    "    \"category\": \"backend\",\n" +
                    "    \"limit\": 20\n" +
                    "  }\n" +
                    "}";

            StringEntity entity = new StringEntity(jsonBody, "UTF-8");
            request.setEntity(entity);

            // Выполняем запрос
            org.apache.http.HttpResponse response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);

            if (statusCode == 200) {
                HttpEntity responseEntity = response.getEntity();
                String result = EntityUtils.toString(responseEntity, "UTF-8");
                System.out.println("Response: " + result);
            } else {
                System.out.println("Error: " + response.getStatusLine().getReasonPhrase());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
    }*/
    public static void main(String[] args) {
        try {
            // URL сервера
            URL url = new URL("https://api.example.com/search");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Устанавливаем метод QUERY
            conn.setRequestMethod("QUERY");

            // Заголовки
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Java/1.6 Query Client");

            // Разрешаем отправку тела запроса
            conn.setDoOutput(true);

            // Тело запроса (JSON или другой формат)
            String jsonBody = "{\n" +
                    "  \"query\": \"java 1.6\",\n" +
                    "  \"filters\": {\n" +
                    "    \"category\": \"programming\",\n" +
                    "    \"limit\": 10\n" +
                    "  }\n" +
                    "}";

            // Отправляем тело
            OutputStream os = conn.getOutputStream();
            os.write(jsonBody.getBytes("UTF-8"));
            os.flush();
            os.close();

            // Получаем ответ
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("Error: " + conn.getResponseMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
