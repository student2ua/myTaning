package obuchenie.log4j_additivity.pckg1;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 20.01.2011
 * Time: 13:13:43
 * To change this template use File | Settings | File Templates.
 */
public class FirstClass {
    private static final Logger logger = Logger.getLogger(FirstClass.class);

    public FirstClass() {
        logger.debug("created!");
    }
}
