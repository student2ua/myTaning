package obuchenie;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 08.09.2008
 * Time: 13:17:12
 * To change this template use File | Settings | File Templates.
 */
public class SystemInfo {
    private static final Logger log = Logger.getLogger(SystemInfo.class);
    private Properties p;
    Map<String, String> envMap;

    public SystemInfo() {
        p = System.getProperties();
        //get unmodifiable environment variables map
        envMap = System.getenv();


        //Get Specific environment variable
        String pathValue = System.getenv("PATH");
        System.out.println("$PATH=" + pathValue);
    }

    //  p.list(System.out);
    public String getInfoOS() {
        StringBuffer sb = new StringBuffer();
        sb.append("********** OS info ***********\n");
        sb.append("os.name = " + p.getProperty("os.name") + "\n");
        sb.append("os.version = " + p.getProperty("os.version") + "\n");
        sb.append("sun.os.patch.level = " + p.getProperty("sun.os.patch.level") + "\n");
        sb.append("os.arch = " + p.getProperty("os.arch") + "\n");
        sb.append("file.encoding= " + p.getProperty("file.encoding") + "\n");
        return sb.toString();
    }

    public String getInfoJava() {
        StringBuffer sb = new StringBuffer();
        sb.append("********** Java info ***********\n");
        sb.append("java.version = " + p.getProperty("java.version") + "\n");
        sb.append("java.vm.version = " + p.getProperty("java.vm.version") + "\n");
        sb.append("java.home = " + p.getProperty("java.home") + "\n");
        sb.append("java.class.version = " + p.getProperty("java.class.version") + "\n");

        return sb.toString();
    }

    public void getInfoALLtoSysOut() {
        p.list(System.out);
    }

    public void getEnvAll() {
        for (String key : envMap.keySet()) {
            System.out.printf("Key=%s,value=%s%n", key, envMap.get(key));
        }
    }

    public static void main(String[] args) {
        System.out.println(new SystemInfo().getInfoOS());
        System.out.println(new SystemInfo().getInfoJava());
        new SystemInfo().getInfoALLtoSysOut();
        System.out.println("-------------");
        new SystemInfo().getEnvAll();
    }
}
