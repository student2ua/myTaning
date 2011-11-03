package obuchenie.javaee.beans.projectentity;

import javax.ejb.EJBHome;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 18:43:04
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectEntityHome extends EJBHome {
    public ProjectEntity findByPrimaryKey(String id);
}
