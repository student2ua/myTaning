package obuchenie.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2011
 * Time: 18:11:34
 * To change this template use File | Settings | File Templates.
 */
public class AuthTicket {
    //    private static final Logger log = Logger.getLogger(AuthTicket.class);
    public static final String CIPHER = "AES/ECB/NoPadding";
    public static final String ALGORITM = "AES";
    public static final byte[] KEY = {
            -67, 9, 115, -85, -46, -3, 55, -27, 104, 100, -38, -29, -40, 79, -53, 87
    };
    private long sysUserId;
    private long timestamp;

    private String ticket;

    public void setTicketData(long sysUserID, long timestamp) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, ALGORITM));
            ByteBuffer byteData = ByteBuffer.allocate(16);
            byteData.putLong(0, sysUserID);
            byteData.putLong(8, timestamp);
            this.ticket = new String(bytesToHex(cipher.doFinal(byteData.array())));
            this.sysUserId = sysUserID;
            this.timestamp = timestamp;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
//            log.error(e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            //log.error(e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
//            log.error(e);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
//            log.error(e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
//            log.error(e);
        }
    }

    public void setTicket(String ticket) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(KEY,ALGORITM));
            ByteBuffer byteData= ByteBuffer.wrap(cipher.doFinal (hexStringToByte(ticket)));
            this.ticket=ticket;
            this.sysUserId=byteData.getLong(0);
            this.timestamp=byteData.getLong(8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
//             log.error(e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
//             log.error(e);
        } catch (InvalidKeyException e) {
e.printStackTrace();
//            log.error(e);
        } catch (IllegalBlockSizeException e) {
e.printStackTrace();
            //log.error(e);
        } catch (BadPaddingException e) {
//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
           // log.error(e);
        }
    }

    public long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    static char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    public static String bytesToHex(byte[] b, int off, int len) {
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < len; j++)
            buf.append(byteToHex(b[off + j]));
        return buf.toString();
    }

    public static String byteToHex(byte b) {
        char[] a = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
        return new String(a);
    }
     public static byte[] hexStringToByte(String hex) {
    byte[] bts = new byte[hex.length() / 2];
    for (int i = 0; i < bts.length; i++) {
      bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return bts;
  }
}
