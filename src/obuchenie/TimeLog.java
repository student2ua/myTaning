package obuchenie;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.08.2008
 * Time: 13:09:24
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;



public interface TimeLog {

    EnumerIterator filter(String path, Date from, Date to) throws IOException;

}