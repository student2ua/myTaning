<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/format" prefix="fmt" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello World</title>
</head>
<body>
<header>
    <h1>Title : countries</h1>
</header>
<form action="countries" method="post">
    <table>
        <c:forEach items="${countries}" var="country">
            <tr>
                <td>${country.countryId}</td>
                <td><c:out value="${fn:escapeXml(country.countryName)}"/></td>
                <td><c:out value="${country.check}"/></td>
                <td><c:out value="${country.sortLevel}"/></td>
                <td><c:out value="${fn:escapeXml(country.capital)}"/></td>
                <td><c:out value="${fn:escapeXml(country.shortName)}"/></td>
                <td><c:out value="${country.codeIso}"/></td>
                <%--<td><fmt:formatNumber value="${country.price}" type="currency" currencyCode="USD"/></td>--%>
                <td><a href="${pageContext.request.contextPath}/country?edit=${country.countryId}">edit</a></td>
                <td>
                    <button type="submit" name="delete" value="${country.countryId}">delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/country">add</a>
</form>
</body>
</html>