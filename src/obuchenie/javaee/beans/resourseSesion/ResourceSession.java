package obuchenie.javaee.beans.resourseSesion;

import obuchenie.javaee.exceptions.ResourceException;
import obuchenie.javaee.exceptions.SkillSetException;
import obuchenie.javaee.exceptions.BlockoutTimeException;
import obuchenie.javaee.vo.ResourceVO;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 20:11:25
 * Remote Interface for ResourceSession
 */
public interface ResourceSession extends EJBObject {
    public ResourceVO setCurrentResource(String resourceId) throws RemoteException, ResourceException;

    public ResourceVO getResourceDetails() throws RemoteException, ResourceException;

    public void setResourceDetails(ResourceVO resource) throws RemoteException, ResourceException;

    public void addResource(ResourceVO resource) throws RemoteException, ResourceException;

    public void removeResource() throws RemoteException, ResourceException;

    // methods for managing blockout time by the   resource
    public void addBlockoutTime(Collection blockoutTime) throws RemoteException, BlockoutTimeException;

    public void updateBlockoutTime(Collection blockoutTime) throws RemoteException, BlockoutTimeException;

    public void removeBlockoutTime(Collection blockoutTime) throws RemoteException, BlockoutTimeException;

    public void removeAllBlockoutTime() throws RemoteException, BlockoutTimeException;

    // methods for resource skillsets time by the   resource
    public void addSkillSets(Collection skillSet) throws RemoteException, SkillSetException;

    public void updateSkillSets(Collection skillSet) throws RemoteException, SkillSetException;

    public void removeSkillSet(Collection skillSet) throws RemoteException, SkillSetException;
}
