#### Tomcat 7.0.27

- [authentication](.\authentication\README.md) jboss
- [doubleSubmitCookie](.\doubleSubmitCookie\readme.md)  csrf & double submit Cookie   -ok  todo fix login form   , embedded svg[] using
- [examples](.\examples\readme.md) разные нюансы использования сервлетов
- [jsf_table](.\jsf_table\readme.md) -ok таблица, с темплатом не очень     jsf 2.2
- jsftutorial чужая работающая херь
- [minimal] минимальный 3й сервлет -ок
- minimalJSF2   просто копия верхнего походу нерабочая !!!!
- restCRUD
- [restCRUD_jsf](.\restCRUD_jsf\readme.md)
- [servletDAO](.\servletDAO\readme.md) -ok, CRUD jstl.functions,   add jquery jTable  вариант
- tablePF
- [tableRF](.\tableRF\readme.md) Sample RichFaces 4 Application создан из архитипа
- UploadServlet30 непроверен

## to do
- [Unit testing a Java Servlet](https://stackoverflow.com/questions/90907/unit-testing-a-java-servlet)

#### Info
JSF 2.2 requires a minimum of  Java 1.6, Servlet 3.0 and EL 2.2.
- https://stackoverflow.com/tags/servlets/info

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<html>
<head>
<title>Страница автоматически обновляется пример программы</title>
</head>
<body>

<h2>https://www.w3big.com/ru/jsp/jsp-auto-refresh.html#gsc.tab=0</h2>
<%
   // 设置每隔5秒刷新一次
   response.setIntHeader("Refresh", 5);
   // 获取当前时间
   Calendar calendar = new GregorianCalendar();
   String am_pm;
   int hour = calendar.get(Calendar.HOUR);
   int minute = calendar.get(Calendar.MINUTE);
   int second = calendar.get(Calendar.SECOND);
   if(calendar.get(Calendar.AM_PM) == 0)
      am_pm = "AM";
   else
      am_pm = "PM";
   String CT = hour+":"+ minute +":"+ second +" "+ am_pm;
   out.println("当前时间为: " + CT + "\n");
%>

</body>
</html>
``` 


