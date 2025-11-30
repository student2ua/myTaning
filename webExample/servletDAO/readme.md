Servlet 3.0 / EL 2.2 capable container such as Tomcat 7, Glassfish 3, JBoss AS 6 not need to use  javax.faces.model.DataModel

http://balusc.omnifaces.org/2010/06/benefits-and-pitfalls-of-viewscoped.html

 ### Links
- http://qaru.site/questions/6234/how-should-i-connect-to-jdbc-database-datasource-in-a-servlet-based-application
- http://qaru.site/questions/2707/show-jdbc-resultset-in-html-in-jsp-page-using-mvc-and-dao-pattern
- https://stackoverflow.com/tags/servlets/info
- https://stackoverflow.com/questions/3541077/design-patterns-web-based-applications
- https://www.mitrais.com/news-updates/step-by-step-making-a-simple-crud-application-using-java-servlet-jsp/

 ### todo

  1 copy oracle driver ~ 10.1.0.3
  `http://192.168.x.x:8081/servletDAO/countries`

  add jquery jTable 2.4.0 variant     ? https://www.webjars.org/  https://github.com/webjars/sample-jetty_war

  ---
  add repo maven.ceon.pl/artifactory/repo - all ver Oracle Drv
  ---
  
 ### issue 
 #### DS not work in Tomcat 9.27
1. Tomcat 9.27 - Java 8
2. Oracle version driver 12.

https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides
  java.sql.SQLException: Cannot create PoolableConnectionFactory (ORA-00604: error occurred at recursive SQL level 1
  ORA-01882: timezone region  not found
  )

##### [variant 1](https://stackoverflow.com/questions/9156379/ora-01882-timezone-region-not-found)
  
  toTomcat catalina.bat
  ```
  JAVA_OPTS=-Doracle.jdbc.timezoneAsRegion=false
  JAVA_OPTS= -Duser.timezone=GMT
  ```

 ORA-00604: ошибка на рекурсивном SQL-уровне 1
 ORA-12705: Указано неверное или неизвестное значение параметра NLS

 При коннекте драйвер сразу говорит ALTER SESSION , сменяя настройки NLS. При этом он берет Locale.getDefault().
 У него есть своя таблица соответсвия локалей явы и параметров для ALTER SESSION. Для локали RU в 8-ке и 9-ке
  **NLS_TERRITORY** была равна **"CIS"**, а для 10-ки стала **RUSSIA**. И драйвер от 10-ки говорит серверу 9-ки
 `ALTER SESSION SET NLS_TERRITORY='RUSSIA'`
 
Естественно сервер 9-ки не понимает такой NLS-TERRITORY
##### variant 2
 В качестве решения проблемы предлагается взять драйвера от Oracle 10.2 и скормить их девелоперу.

>>>> старый  драйвер 10,2 вернул java.lang.AbstractMethodError: oracle.jdbc.driver.T4CConnection.isValid(I)Z

>>>> ojdbc8.jar драйвер + доп файлы orai18n.jar ucp.jar не помогает

SELECT  * FROM  NLS_DATABASE_PARAMETERS -> NLS_TERRITORY=AMERICA

>>>>>> -Duser.language=en -Duser.region=en ?   Не читаются русс символы o12
