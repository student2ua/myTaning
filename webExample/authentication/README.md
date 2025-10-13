### links

* [Basic Authentication in WebApplications on JBoss AS6](http://middlewaremagic.com/jboss/?p=220)  use
* [JBoss authentication issue](https://stackoverflow.com/questions/35172966/jboss-authentication-issue)

# Basic Authentication in WebApplications on JBoss AS6

Securing the Web Applications is one of the primary goal while developing an application for production environments. Basic authentication is one of the most used technique which allows applications to popup a window to enter the user credentials while accessing a secured resource from the server. In Case of basic authentication you will see a special authentication header (with value base64encode(username+”:”+password)) to the original request and re-sends it.

Here we will see how to develop a simple WebApplication based on basic authentication feature on JBoss AS6.

Step1). Create a directory somewhere in your file system with some name like “C:/BasicAuthWebApp.war”.

Step2). Create a “index.jsp” page like following inside directory “C:/BasicAuthWebApp.war”.
```
<html>
<head><title>Welcome Page !!!</title></head>
<body>
<center>
<h3> Hi this is not a secured page so it won't ask for Basic Authentication Credentials.
<h3> <a href="secured/protected.jsp">Click Here to access a Secured Resource</a></h3>
</center>
<%
    System.out.println("nnt index.jsp called");
%>
</body>
</html>
```

Step3). Create another directory with some name like “secured” inside “C:/BasicAuthWebApp.war” directory. And then write a jsp with some name “protected.jsp” inside “C:/BasicAuthWebApp.war/secured” directory , like following:

```
<html>
<head><title>secured Resource</title></head>
<body>
<center>
<h1><font color=red>You are accessing a secured Resource (protected.jsp)</font></h1>
<a href="../logout.jsp">Click Here to Logout</a>
</center>
</body>
</html>
```

Create a file with name “logout.jsp” inside “C:/BasicAuthWebApp.war” like following:

```
<html>
<body>
<%
           if(session!=null)
             {
                   session.invalidate();
%>
                <jsp:forward page="index.jsp" />
<%
              } else{
%>
            Logged Out Successfully....
<% }%>
</body>
</html>
```

Step4). Create “WEB-INF” directory inside “C:/BasicAuthWebApp.war” and then place a “web.xml” file inside “C:/BasicAuthWebApp.war/WEB-INF” directory as following:

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.5"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
    <security-constraint>
        <web-resource-collection>
            <web-resource-name>MySecureResources</web-resource-name>
            <description>Some Description</description>
            <url-pattern>/secured/*</url-pattern>
            <http-method>GET</http-method>
            <http-method>POST</http-method>
        </web-resource-collection>
        <auth-constraint>
            <role-name>TestRole</role-name>
        </auth-constraint>
    </security-constraint>
    <login-config>
        <auth-method>BASIC</auth-method>
    </login-config>
    <security-role>
        <role-name>TestRole</role-name>
    </security-role>
</web-app>
```

Step5). Similarly create another file with name “jboss-web.xml” inside “C:/BasicAuthWebApp.war/WEB-INF” like following:

```
<?xml version="1.0"?>
<!DOCTYPE jboss-web PUBLIC
        "-//JBoss//DTD Web Application 5.0//EN"
        "http://www.jboss.org/j2ee/dtd/jboss-web_5_0.dtd">
<jboss-web>
    <security-domain>java:/jaas/BasicAuthWebAppPolicy</security-domain>
    <context-root>/basicSecurityWebApp</context-root>
</jboss-web>
```

Step6). Create a file with some name like “C:/BasicAuthWebApp.war/WEB-INF/classes/basicSecurityWebApp-roles.properties” like following to define the Role Name and to assign that role to some user:

`TestUserOne=TestRole`

Step7). Create another file with some name like “C:/BasicAuthWebApp.war/WEB-INF/classes/basicSecurityWebApp-users.properties” like following to define the username and his password:

`TestUserOne=TestPassword`

Step8). As in step-5 we can see that we are using a security-domain so we will have to create an “application-policy” inside “$PROFILE/conf/login-config.xml” file with the same name as “BasicAuthWebAppPolicy”. So edit the “login-config.xml” file by adding the following tag set inside it, as following:
```
  <application-policy name="BasicAuthWebAppPolicy">
    <authentication>
      <login-module code="org.jboss.security.auth.spi.UsersRolesLoginModule" flag="required">
        <module-option name="usersProperties">basicSecurityWebApp-users.properties</module-option>
        <module-option name="rolesProperties">basicSecurityWebApp-roles.properties</module-option>
      </login-module>
    </authentication>
  </application-policy>
```
Step9). Now as the application is ready so copy and paste the “BasicAuthWebApp.war” folder inside the “$PROFILE/deploy” directory and then after restarting your JBoss AS6 instance , try accessing the application.

Step10). Once you will try to access the URL “http://localhost:8080/basicSecurityWebApp/secured/protected.jsp” Container will ask for security credentials, enter the username as “TestUserOne” and password as “TestPassword”.

Step11). For any subsequent request and response to this web application you will notice that a special header something like this “Authorization: Basic amFjazpwYXNzd29yZA==” gets added. Notice the line-14 in the below snippet.

http://localhost:8080/basicSecurityWebApp/secured/protected.jsp

#### request below

```
GET /basicSecurityWebApp/secured/protected.jsp HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:6.0.2) Gecko/20100101 Firefox/6.0.2
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-us,en;q=0.5
Accept-Encoding: gzip, deflate
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Connection: keep-alive
Referer: http://localhost:8080/basicSecurityWebApp/
Cookie: JSESSIONID=6E8EDC1D2041CF7169994E67C3024588
Authorization: Basic amFjazpwYXNzd29yZA==
Cache-Control: max-age=0
```

#### response below

```
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Pragma: No-cache
Cache-Control: no-cache
Expires: Thu, 01 Jan 1970 05:30:00 IST
X-Powered-By: Servlet 2.5; JBoss-5.0/JBossWeb-2.1
Content-Type: text/html;charset=ISO-8859-1
Content-Length: 179
Date: Sat, 24 Sep 2011 19:18:09 GMT
```