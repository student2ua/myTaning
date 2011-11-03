package obuchenie.javaee.beans.projectresursemanager;

import obuchenie.javaee.exceptions.BlockoutTimeException;
import obuchenie.javaee.exceptions.PSAException;
import obuchenie.javaee.exceptions.ProjectException;
import obuchenie.javaee.exceptions.ResourceException;
import obuchenie.javaee.vo.ProjectVO;
import obuchenie.javaee.vo.ResourceVO;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 19:14:19
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectResourceManager extends EJBObject {
    public void resetEntities(String resourceId, String projectId) throws RemoteException, ResourceException;

    public void assignResourceToProject(int numHours) throws RemoteException, ResourceException;

    public void unassignResourceFromProject() throws RemoteException, ResourceException;

    public ResourceVO getResourceData() throws RemoteException, ResourceException;

    public void setResourceData(ResourceVO resource) throws RemoteException, ResourceException;

    public ResourceVO createNewResource(ResourceVO resource) throws ResourceException;

    public void addBlockoutTime(Collection blockoutTime) throws RemoteException, BlockoutTimeException;

    public void updateBlockoutTime(Collection blockoutTime) throws RemoteException, BlockoutTimeException;

    public Collection getResourceCommitments() throws RemoteException, ResourceException;

    public ProjectVO getProjectData() throws RemoteException, ProjectException;

    public void setProjectData(ProjectVO project) throws RemoteException, ProjectException;

    public ProjectVO createNewProject(ProjectVO project) throws RemoteException, ProjectException;


    public ProjectCVO getProjectDetailsData() throws RemoteException, PSAException;

    public Collection getProjectsList(Date start, Date end) throws RemoteException, PSAException;

}
