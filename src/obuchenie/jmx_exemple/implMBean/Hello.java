package obuchenie.jmx_exemple.implMBean;

import obuchenie.jmx_exemple.HelloMBean;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.05.2010
 * Time: 12:31:52
 * To change this template use File | Settings | File Templates.
 */
public class Hello implements HelloMBean {
    private String message = null;

    public Hello() {
        this.message = "Ky";
    }

    public Hello(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void sayHello() {
        System.out.println(message);
    }
}
