# JBoss examples
- [EJBCallbacks](./EJBCallbacks/README.md)
- [JBossRS](./JBossRS/README.md)
- [jms Sample](./jmsSample/README.md)
- netty
   - [netty-as-proxy](./netty/readme.md)
   - netty-echo
   - netty-uptime-client
 
 ### Links
- [Maven Archetypes](https://maven.apache.org/archetypes/index.html) 
- [GitHub](https://github.com/kwart/jboss-eap-quickstart) 6.1 AS && 7.1 Final,  Java 1.6  

## libs java ee 6 
### Jboss 7.1
#### server 
```
    <dependency>
        <groupId>org.jboss.spec</groupId>
        <artifactId>jboss-javaee-6.0</artifactId>
        <version>${version.org.jboss.spec.jboss.javaee.6.0}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
``` 
#### client
``` 
 <dependency>
   <groupId>org.jboss.as</groupId>
   <artifactId>jboss-as-ejb-client-bom</artifactId>
   <version>${version.org.jboss.as}</version>
   <type>pom</type>
   <scope>import</scope>
 </dependency>
```
