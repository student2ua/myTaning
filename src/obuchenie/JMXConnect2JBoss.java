package obuchenie;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.naming.Context;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.04.12
 * Time: 13:28
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.tikalk.com/java/forums/jmx-connection-jboss
 */
public class JMXConnect2JBoss {
    public static void main(String[] args) throws Exception {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        URLClassLoader cl = new URLClassLoader(new URL[]{new URL("JBOSS_HOME/client/jbossall-client.jar")});
        Thread.currentThread().setContextClassLoader(cl);
        Class<Context> clazz = (Class<Context>) cl.loadClass("javax.naming.InitialContext");

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        env.put(Context.PROVIDER_URL, String.format("jnp://%s:%d", getJmxHostname(), getJmxPort()));
        Constructor<Context> ctor = clazz.getConstructor(Hashtable.class);
        Context ctx = ctor.newInstance(env);
        Method method = clazz.getMethod("lookup", String.class);
        MBeanServerConnection conn = (MBeanServerConnection) method.invoke(ctx, "jmx/invoker/RMIAdaptor");
        Thread.currentThread().setContextClassLoader(currentClassLoader);

        ObjectName name = ObjectName.getInstance("jboss.system", "type", "Server");
        Boolean started = (Boolean) conn.getAttribute(name, "Started");

    }
}
