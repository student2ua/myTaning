package obuchenie.javaee.exceptions;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 18:55:03
 * To change this template use File | Settings | File Templates.
 */
public class ProjectException extends PSAException {
    private static final Logger log = Logger.getLogger(ProjectException.class);

    public ProjectException(Exception e) {
        log.error(e);
    }
}
