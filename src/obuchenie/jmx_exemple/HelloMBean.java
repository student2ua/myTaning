package obuchenie.jmx_exemple;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.05.2010
 * Time: 12:30:55
 * To change this template use File | Settings | File Templates.
 */
public interface HelloMBean {
    public void setMessage(String message);

    public String getMessage();

    public void sayHello();
}
