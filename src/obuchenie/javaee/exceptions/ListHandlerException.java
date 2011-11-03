package obuchenie.javaee.exceptions;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 21:07:11
 * To change this template use File | Settings | File Templates.
 */
public class ListHandlerException extends Exception {
    private static final Logger log = Logger.getLogger(ListHandlerException.class);

    public ListHandlerException(String s) {
        log.error(s);
    }
}
