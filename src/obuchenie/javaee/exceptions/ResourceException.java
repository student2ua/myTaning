package obuchenie.javaee.exceptions;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 20:13:07
 * To change this template use File | Settings | File Templates.
 */
public class ResourceException extends PSAException {
    private static final Logger log = Logger.getLogger(ResourceException.class);

    public ResourceException(Exception e) {
        log.error(e);
    }
}
