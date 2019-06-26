<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello World</title>
</head>
<body>
<header>
    <h1>Title : country</h1>
</header>
<form action="country" method="post">
    <label for="countryName">Name</label>
    <input id="countryName" name="countryName" value="${fn:escapeXml(country.countryName)}" type="text" style="width: 300em;" />
    <br/>
    <label for="isCheck">isCheck</label>
    <input id="isCheck" name="isCheck" value="${fn:escapeXml(country.check)}" type="checkbox"/>
    <br/>
    <label for="sortLevel">sortLevel</label>
    <input id="sortLevel" name="sortLevel" value="${fn:escapeXml(country.sortLevel)}"/>
    <br/>
    <label for="capital">capital</label>
    <input id="capital" name="capital" value="${fn:escapeXml(country.capital)}" type="text" style="width: 300em;"/>
    <br/>
    <label for="shortName">shortName</label>
    <input id="shortName" name="shortName" value="${fn:escapeXml(country.shortName)}" type="text" style="width: 300em;"/>
    <br/>
    <label for="codeIso">codeIso</label>
    <input id="codeIso" name="codeIso" value="${fn:escapeXml(country.codeIso)}"/>
    <br/>
    <button type="submit" name="save" value="${country.countryId}">save</button>
</form>
</body>
</html>