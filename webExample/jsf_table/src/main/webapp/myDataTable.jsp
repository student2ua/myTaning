<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello ${name}</title>

</head>
<body>
<h:dataTable value="#{usersBean.userList}" var="user" border="1">
    <f:facet name="header">
        <h:outputText value="User"/>
    </f:facet>
    <h:column>
        <f:facet name="header">
            <h:outputText value="Fist name"/>
        </f:facet>
        <h:outputText value="#{user.firstName}"/>
    </h:column>
    <h:column>
        <f:facet name="header">
            <h:outputText value="Last name"/>
        </f:facet>
        <h:outputText value="#{user.lastName}"/>
    </h:column>
    <h:column>
        <f:facet name="header">
            <h:outputText value="Email"/>
        </f:facet>
        <h:outputText value="#{user.email}"/>
    </h:column>
    <h:column>
        <f:facet name="header">
            <h:outputText value="Age"/>
        </f:facet>
        <h:outputText value="#{user.age}"/>
    </h:column>
    <f:facet name="footer">
        <h:outputText value="User count"/>
        <h:outputText value="#{usersBean.userListSize}"/>
    </f:facet>
</h:dataTable>
</body>
</body>
</html>