<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 14.04.19
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JMS пример</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <%--<script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>--%>
    <script>
        function sendMessages() {
            $.ajax({
                url: 'service',
                data: {
                    mode: 'send',
                    prefix: document.getElementById('prefix').value
                },
                success: function (response) {
                    $('#sendJMS').html(response);
                }
            });
        }
        function receiveMessages() {
            $.ajax({
                url: 'service',
                data: {
                    mode: 'receive' },
                success: function (response) {
                    $('#receiveJMS').html(response);
                }
            });
        }
    </script>
</head>
<body>
<h2>Пример JMS с использованием JBoss</h2>

<p>Отправьте сообщения JBoss с префиксом <input id="prefix" type="text" size="5"></p>
<input type="submit" width="80" value="Отправить" onClick="sendMessages()">

<p/>
<span id="sendJMS" style="color: #3f48cc;"> </span>

<p>Получите сообщения из очереди JBoss</p>
<input type="submit" width="80" value="Получить" onClick="receiveMessages()">

<p/>
<span id="receiveJMS" style="color: #7349a4;"> </span>
</body>
</html>