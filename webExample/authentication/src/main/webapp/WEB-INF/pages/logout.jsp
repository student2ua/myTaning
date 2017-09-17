<%--
  User: Admin
  Date: 17.09.17
  Time: 21:06
  http://stackoverflow.com/questions/35172966/jboss-authentication-issue
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<%
    if(session!=null)
    {
        session.invalidate();%>
<jsp:forward page="index.jsp" />
<%
} else{
%>
Logged Out Successfully....
<% }%>
</body>
</html>