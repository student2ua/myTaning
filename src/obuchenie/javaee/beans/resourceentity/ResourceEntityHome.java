package obuchenie.javaee.beans.resourceentity;

import javax.ejb.EJBHome;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 18:36:14
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceEntityHome extends EJBHome {
    public ResourceEntity findByPrimaryKey(String id);
}
