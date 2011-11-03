package obuchenie.javaee.exceptions;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 21:00:55
 * To change this template use File | Settings | File Templates.
 */
public class IteratorException extends Exception {
    private static final Logger log = Logger.getLogger(IteratorException.class);

    public IteratorException(String s) {
        log.error(s);
    }
}
