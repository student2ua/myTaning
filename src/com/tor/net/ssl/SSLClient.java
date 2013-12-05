package com.tor.net.ssl;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
/**
 * User: tor
 * Date: 05.12.13
 * Time: 11:43
 */
public class SSLClient {
    /** Creates a new instance of SSLClient */
    public SSLClient() {
    }
    public static void main(String[] args) {
        try {
            // Получить экземпляр хранилища ключей.
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("ClientKeyStore");
            keyStore.load(fis, "clientpassword".toCharArray());

            // Получить диспетчеры ключей базовой реализации для заданного хранилища ключей.
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, "clientkeypassword".toCharArray());
            KeyManager [] keyManagers = keyManagerFactory.getKeyManagers();

            // Получить доверенные диспетчеры базовой реализации.
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager [] trustManagers = trustManagerFactory.getTrustManagers();

            // Получить защищенное случайное число.
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");

            // Создание SSL контекста
            SSLContext sslContext = SSLContext.getInstance("SSLv3");
            sslContext.init(keyManagers, trustManagers, secureRandom);

            // Создание фабрики SSL сокетов.
            javax.net.ssl.SSLSocketFactory sslSocketFactory =
                    sslContext.getSocketFactory();

            // Создаем сокет.
            System.out.println("Creating Server Socket : 1234");
            SSLSocket sslClientSocket = (SSLSocket) sslSocketFactory.createSocket("localhost", 1234);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(sslClientSocket.getInputStream()));

            while (true) {
                String s = br.readLine();
                if (s.length() > 0) {
                    System.out.println( s );
                    break;
                }
            }
            br.close();
            System.out.println("Test connection complite successfully");
        } catch (KeyManagementException ex) {
            ex.printStackTrace();
        } catch (UnrecoverableKeyException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        } catch (CertificateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
