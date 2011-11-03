package obuchenie;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.05.2010
 * Time: 13:59:49
 * To change this template use File | Settings | File Templates.
 */
public class GetMyIp {
    private static final Logger log = Logger.getLogger(GetMyIp.class);

    public static void main(String[] args) {

        try {
            
            InetAddress address = InetAddress.getByName(null);
            System.out.println("address.getByName(null); = " + address);
            address = InetAddress.getLocalHost();
            System.out.println("address.getLocalHost = " + address);
            System.out.println("address.getLocalHost.getCanonicalHostName() = " + address.getCanonicalHostName());
            System.out.println("address.getLocalHost.getHostAddress() = " + address.getHostAddress());
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            log.error(e);
        }
    }

}
