package obuchenie.log4j_additivity.pckg2;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 20.01.2011
 * Time: 13:15:53
 * To change this template use File | Settings | File Templates.
 */
public class SecondClass {
    private static final Logger logger = Logger.getLogger(SecondClass.class);

    public SecondClass() {
        logger.debug("created!");
    }
}
