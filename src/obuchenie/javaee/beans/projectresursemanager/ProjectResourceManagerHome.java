package obuchenie.javaee.beans.projectresursemanager;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 19:34:33
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectResourceManagerHome extends EJBHome {

    public ProjectResourceManager create() throws RemoteException, CreateException;

    public ProjectResourceManager create(String resourceId, String projectId)
            throws RemoteException, CreateException;
}
