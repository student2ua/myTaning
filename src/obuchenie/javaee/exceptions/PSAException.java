package obuchenie.javaee.exceptions;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 18:40:52
 * To change this template use File | Settings | File Templates.
 */
public class PSAException extends Exception {
    private static final Logger log = Logger.getLogger(PSAException.class);

    public PSAException(String s) {
        log.error(s);
    }
}
