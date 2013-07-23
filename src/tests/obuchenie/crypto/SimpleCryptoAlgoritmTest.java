package obuchenie.crypto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

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
    public void MD5_exemple() {
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
    public void PBKDF2WithHmacSHA1_with_Salt_exemple() {
        String passwordToHash = "password";
        int iterations = 1000;
        String generatedPassword = null;
        byte[] salt = new byte[0];
        try {

            char[] chars = passwordToHash.toCharArray();
            salt = getSalt().getBytes();

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            generatedPassword = AuthTicket.bytesToHex(hash, 0, hash.length);

        } catch (Exception e) {
            Assert.fail(e.toString());
        }
        System.out.println(iterations);
        System.out.println(AuthTicket.bytesToHex(salt, 0, salt.length));
        System.out.println(generatedPassword);
    }
}
