package obuchenie.jmx_exemple;

import obuchenie.jmx_exemple.implMBean.Hello;
import org.apache.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.05.2010
 * Time: 12:47:50
 * To change this template use File | Settings | File Templates.
 */
public class SimpleAgent {
    private static final Logger log = Logger.getLogger(SimpleAgent.class);
    private MBeanServer mbs = null;

    public SimpleAgent() {
        Registry reg = null;

        try {
            if (reg == null) {
                reg = LocateRegistry.createRegistry(1099);
            }
        } catch (Throwable t) {
            System.out.println(("Can’t start registry–it may be already started:" + t));
        }

        // Get the platform MBeanServer
        //  mbs = ManagementFactory.getPlatformMBeanServer();
        mbs = MBeanServerFactory.createMBeanServer("SimpleAgent");
        // Unique identification of MBeans
        Hello helloBean = new Hello();
        helloBean.setMessage("HELLO - HELLO - HELLO");
        ObjectName helloName = null;

        try {
            // Uniquely identify the MBeans and register them with the platform MBeanServer
            helloName = new ObjectName("SimpleAgent:name=SwingHelloThere");
            mbs.registerMBean(helloBean, helloName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java -Dcom.sun.management.jmxremote \
     * -Dcom.sun.management.jmxremote.port=1616 \
     * -Dcom.sun.management.jmxremote.authenticate=false \
     * -Dcom.sun.management.jmxremote.ssl=false \
     * SimpleAgent
     */
    public static void main(String argv[]) {
        SimpleAgent agent = new SimpleAgent();
        System.out.println("SimpleAgent is running...");
        new PingPong("ping", 2000, 1000).start();
        new PingPong("  pong", 5000, 500).start();
        new PingPong(" ding", 4000, 500).start();
        new PingPong("   dong", 3000, 500).start();
        new PingPong("PONG", 2800, 500).start();
    }

}
