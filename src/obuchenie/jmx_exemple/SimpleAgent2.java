package obuchenie.jmx_exemple;

import obuchenie.jmx_exemple.implMBean.Hello;
import org.apache.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.05.2010
 * Time: 12:36:20
 * To change this template use File | Settings | File Templates.
 */
public class SimpleAgent2 {
    private static final Logger log = Logger.getLogger(SimpleAgent2.class);
    private MBeanServer server = null;

    public SimpleAgent2() {
        // server = ManagementFactory.getPlatformMBeanServer();
        server = MBeanServerFactory.createMBeanServer("SimpleAgent2");
        // Unique identification of MBeans
        HelloMBean helloBean = new Hello();
        ObjectName helloName = null;

        try {
            // Uniquely identify the MBeans and register them with the platform MBeanServer
            helloName = new ObjectName("FOO:name=SwingHelloThere");
            server.registerMBean(helloBean, helloName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java -Dcom.sun.management.jmxremote \
     * -Dcom.sun.management.jmxremote.port=1617 \
     * -Dcom.sun.management.jmxremote.authenticate=false \
     * -Dcom.sun.management.jmxremote.ssl=false \
     * SimpleAgent
     */
    public static void main(String argv[]) {
        SimpleAgent2 agent = new SimpleAgent2();
        System.out.println("SimpleAgent2 is running...");
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
