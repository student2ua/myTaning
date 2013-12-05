package com.tor.net.ssl;

import javax.net.ssl.*;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * User: tor
 * Date: 05.12.13
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
public class SSLServer {
    /**
     * Creates a new instance of Main
     */
    public SSLServer() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // Получить экземпляр хранилища ключей.
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("ServerKeyStore");
            keyStore.load(fis, "serverpassword".toCharArray());

            // Получить диспетчеры ключей базовой реализации для заданного хранилища ключей.
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, "serverkeypassword".toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            // Получить доверенные диспетчеры базовой реализации.
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            // Получить защищенное случайное число.
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");

            // Создание SSL контекста
            SSLContext sslContext = SSLContext.getInstance("SSLv3");
            sslContext.init(keyManagers, trustManagers, secureRandom);

            // Создание фабрики SSL сокетов.
            javax.net.ssl.SSLServerSocketFactory sslSocketFactory =
                    sslContext.getServerSocketFactory();

            System.out.println("Creating Server Socket : 1234");

            SSLServerSocket sslServerSocket = (SSLServerSocket)
                    sslSocketFactory.createServerSocket(1234);
            sslServerSocket.setNeedClientAuth(true);

            // Далее работаем как с обычным сокетом.
            System.out.println("Start Listenning Server Socket...");
            SSLSocket sslClientSocket = (SSLSocket) sslServerSocket.accept();
            System.out.println("Client connetion detected.");
            System.out.println("Sending callback message...");
            DataOutputStream os = new DataOutputStream(sslClientSocket.getOutputStream());
            os.write("Sending simple string to socket".getBytes());

            System.out.println("Test connection complite successfully");
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
