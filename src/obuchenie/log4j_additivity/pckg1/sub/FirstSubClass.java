package obuchenie.log4j_additivity.pckg1.sub;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 20.01.2011
 * Time: 13:14:54
 * To change this template use File | Settings | File Templates.
 */
public class FirstSubClass {
    private static final Logger logger = Logger.getLogger(FirstSubClass.class);

    public FirstSubClass() {
        logger.debug("created!");
    }
}
