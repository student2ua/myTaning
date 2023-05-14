### How to use TLS 1.2 in Java 6
- Update the JDK with the latest [Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 6](https://www.oracle.com/java/technologies/jce-6-download.html)
- Download the Bounce Castle files [bcprov-jdk15to18-165.jar](https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk15to18/1.71/bcprov-jdk15to18-1.71.jar) and 
[bctls-jdk15to18-165.jar](https://repo1.maven.org/maven2/org/bouncycastle/bctls-jdk15to18/1.71/bctls-jdk15to18-1.71.jar) and copy them into your ${JAVA_HOME}/jre/lib/ext folder
- Modify the file ${JAVA_HOME}/jre/lib/security/java.security comment out the providers section and add add some extra lines
 ```
  # Original security providers (just comment it)
     # security.provider.1=sun.security.provider.Sun
     # security.provider.2=sun.security.rsa.SunRsaSign
     # security.provider.3=com.sun.net.ssl.internal.ssl.Provider
     # security.provider.4=com.sun.crypto.provider.SunJCE
     # security.provider.5=sun.security.jgss.SunProvider
     # security.provider.6=com.sun.security.sasl.Provider
     # security.provider.7=org.jcp.xml.dsig.internal.dom.XMLDSigRI
     # security.provider.8=sun.security.smartcardio.SunPCSC
 
     # Add the Bouncy Castle security providers with higher priority
     security.provider.1=org.bouncycastle.jce.provider.BouncyCastleProvider
     security.provider.2=org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
 
     # Original security providers with different priorities
     security.provider.3=sun.security.provider.Sun
     security.provider.4=sun.security.rsa.SunRsaSign
     security.provider.5=com.sun.net.ssl.internal.ssl.Provider
     security.provider.6=com.sun.crypto.provider.SunJCE 
     security.provider.7=sun.security.jgss.SunProvider
     security.provider.8=com.sun.security.sasl.Provider
     security.provider.9=org.jcp.xml.dsig.internal.dom.XMLDSigRI
     security.provider.10=sun.security.smartcardio.SunPCSC
 
     # Here we are changing the default SSLSocketFactory implementation
     ssl.SocketFactory.provider=org.bouncycastle.jsse.provider.SSLSocketFactoryImpl
```
### How to use TLS 1.2 in Java 7
 [Enabling TLS v1.2 in Java 7](https://www.baeldung.com/java-7-tls-v12)

https://stackoverflow.com/questions/1037590/which-cipher-suites-to-enable-for-ssl-socket/23365536#23365536

я импортировал `import org.bouncycastle.jce.provider.BouncyCastleProvider;` в моем файле  в​проекте и перед сборкой  `sslContext` включил эту строку
`Security.addProvider(new BouncyCastleProvider());`, которая помогла мне получить необходимые шифры, но для исключения ненужных шифров приведенные выше
хорошие объяснения/примеры