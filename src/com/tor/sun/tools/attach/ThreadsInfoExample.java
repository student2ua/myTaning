package com.tor.sun.tools.attach;

import com.sun.management.ThreadMXBean;
import com.sun.tools.attach.VirtualMachine;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.Set;

/**
 * User: tor
 * Date: 019, 19.09.2023
 * Time: 22:03
 */
public class ThreadsInfoExample {
    // use
    // `> jps`
    // `> java -cp %JAVA_HOME%/lib/tools.jar;. ThreadsInfoExample 4276`
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Please provide process id");
            System.exit(-1);
        }
        VirtualMachine vm = VirtualMachine.attach(args[0]);
        String connectorAddr = vm.getAgentProperties().getProperty(
                "com.sun.management.jmxremote.localConnectorAddress");
        if (connectorAddr == null) {
            String agent = vm.getSystemProperties().getProperty(
                    "java.home")+ File.separator+"lib"+File.separator+
                    "management-agent.jar";
            vm.loadAgent(agent);
            connectorAddr = vm.getAgentProperties().getProperty(
                    "com.sun.management.jmxremote.localConnectorAddress");
        }
        JMXServiceURL serviceURL = new JMXServiceURL(connectorAddr);
        JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
        MBeanServerConnection mbsc = connector.getMBeanServerConnection();
        ObjectName objName = new ObjectName(
                ManagementFactory.THREAD_MXBEAN_NAME);
        Set<ObjectName> mbeans = mbsc.queryNames(objName, null);
        for (ObjectName name: mbeans) {
            ThreadMXBean threadBean;
            threadBean = ManagementFactory.newPlatformMXBeanProxy(
                    mbsc, name.toString(), ThreadMXBean.class);
            long threadIds[] = threadBean.getAllThreadIds();
            for (long threadId: threadIds) {
                ThreadInfo threadInfo = threadBean.getThreadInfo(threadId);
                System.out.println (threadInfo.getThreadName() + " / " +
                        threadInfo.getThreadState());
            }
        }
    }
}
