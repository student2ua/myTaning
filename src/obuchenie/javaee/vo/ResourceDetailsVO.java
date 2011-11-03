package obuchenie.javaee.vo;

import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 16:41:56
 * Multiple Value Objects Strategy Ð ResourceDetailsVO
 */
public class ResourceDetailsVO {
    private static final Logger log = Logger.getLogger(ResourceDetailsVO.class);
    public String resourceId;
    public String lastName;
    public String firstName;
    public String department;
    public String grade;
    // other data...
    public Collection commitments;
    public Collection blockoutTimes;
    public Collection skillSets;
}
