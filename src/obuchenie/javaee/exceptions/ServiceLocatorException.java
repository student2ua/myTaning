package obuchenie.javaee.exceptions;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.04.2010
 * Time: 20:07:20
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLocatorException extends Exception {
    private static final Logger log = Logger.getLogger(ServiceLocatorException.class);

    public ServiceLocatorException(Exception e) {
        log.error(e);
    }

    public ServiceLocatorException(String e) {
        log.error(e);
    }
}
