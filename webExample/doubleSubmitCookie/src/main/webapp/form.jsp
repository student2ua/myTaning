<%--
  Created by IntelliJ IDEA.
  User: tor
  Date: 029, 29.08.2021
  Time: 23:47
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>form</title>
    <script>
        function addHiddenInput() {
            var element1 = document.createElement("input");
            var cookie = getCookieData("doubleSubCookie");
            element1.type = "hidden";
            element1.value = cookie;
            element1.name = "tokenval";
            document.getElementById("form").appendChild(element1);
        }

        function getCookieValue(cname) {
            var name = cname + "=";
            var decodedCookie = decodeURIComponent(document.cookie);
            var ca = decodedCookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') {
                    c = c.substring(1);
                }
                if (c.indexOf(name) == 0) {
                    return c.substring(name.length, c.length)
                }
            }
            return "";
        }
        function getCookieData( name ) {
            var pairs = document.cookie.split("; "),
                count = pairs.length, parts;
            while ( count-- ) {
                parts = pairs[count].split("=");
                if ( parts[0] === name )
                    return parts[1];
            }
            return false;
        }
    </script>
</head>
<body>
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) userName = cookie.getValue();

        }
    }
    if (userName == null) response.sendRedirect("login.jsp");
%>
<form method="post" action="home" name="form" id="form">
    <table>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name" autocomplete="family-name"></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><input type="text" name="address" autocomplete="shipping"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" name="add" onclick="addHiddenInput()"></td>
        </tr>
    </table>

</form>
<form method="post" action="logOut" name="logOutForm" id="logOutForm">
    <input type="submit" value="Logout">
</form>
</body>
</html>
