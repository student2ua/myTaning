package obuchenie.crypto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 23.07.13
 * Time: 14:08
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
@RunWith(JUnit4.class)
public class SimpleCryptoAlgoritmTest {
    @Test
    public void MD5_example() {
        String passwordToHash = "password";
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format in AuthTicket.bytesToHex
            //Get complete hashed password in hex format
            generatedPassword = AuthTicket.bytesToHex(bytes, 0, bytes.length);
        } catch (NoSuchAlgorithmException e) {
            Assert.fail(e.toString());
        }
        System.out.println(generatedPassword);
        Assert.assertEquals(generatedPassword, "5f4dcc3b5aa765d61d8327deb882cf99".toUpperCase());
    }

    @Test
    public void SHA384_example() throws Exception {
        String passwordToHash = "password";
        String generatedPassword = null;
        // Create MessageDigest instance for SHA384
        MessageDigest md = MessageDigest.getInstance("SHA-384"); //SHA-256, SHA-512
        //Add password bytes to digest
        md.update(passwordToHash.getBytes());
        //Get the hash's bytes
        byte[] bytes = md.digest();
        System.out.println("bytes.length = " + bytes.length);
        System.out.println("bytes = " + Arrays.toString(bytes));
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format in AuthTicket.bytesToHex
        //Get complete hashed password in hex format
        generatedPassword = AuthTicket.bytesToHex(bytes, 0, bytes.length);
        System.out.println("Hex= " + generatedPassword);
        Assert.assertEquals(generatedPassword, "A8B64BABD0ACA91A59BDBB7761B421D4F2BB38280D3A75BA0F21F2BEBC45583D446C598660C94CE680C47D19C30783A7");
    }

    /**
     * the SecureRandom class supports the "SHA1PRNG" pseudo random number generator algorithm
     */
    private static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return Arrays.toString(salt);
    }

    @Test
    public void MD5_with_Salt_exemple() {
        String passwordToHash = "password";
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(getSalt().getBytes());

            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format in AuthTicket.bytesToHex
            //Get complete hashed password in hex format
            generatedPassword = AuthTicket.bytesToHex(bytes, 0, bytes.length);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
        System.out.println(generatedPassword);
    }

    @Test
    public void PBKDF2WithHmacSHA1_with_Salt_exemple() throws Exception {
        String passwordToHash = "password";
        int iterations = 1000;
        String generatedPassword = null;
        byte[] salt = new byte[0];


        char[] chars = passwordToHash.toCharArray();
        salt = getSalt().getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        System.out.println("spec.getKeyLength() = " + spec.getKeyLength());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        generatedPassword = AuthTicket.bytesToHex(hash, 0, hash.length);


        System.out.println("Iterations= " + iterations);
        System.out.println("Hex Salt= " + AuthTicket.bytesToHex(salt, 0, salt.length));
        System.out.println("Hex hash= " + generatedPassword);
    }

    @Test
    public void testGetListSecurityProvider() {
        Provider provider[] = Security.getProviders();
        for (Provider pro : provider) {
            System.out.println(pro);
            SortedSet sortedSet=new TreeSet();
            for (Enumeration e = pro.keys(); e.hasMoreElements(); )
                sortedSet.add(e.nextElement());
            for (Object o : sortedSet) {
                System.out.println(" \t" + o);
            }

        }

    }
}
