package obuchenie.javaee.beans.projectresursemanager;

import obuchenie.javaee.ServiceLocator;
import obuchenie.javaee.beans.projectentity.ProjectEntity;
import obuchenie.javaee.beans.projectentity.ProjectEntityHome;
import obuchenie.javaee.beans.resourceentity.ResourceEntity;
import obuchenie.javaee.beans.resourceentity.ResourceEntityHome;
import obuchenie.javaee.exceptions.*;
import obuchenie.javaee.vo.ProjectVO;
import obuchenie.javaee.vo.ResourceVO;
import org.apache.log4j.Logger;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 17:52:14
 * To change this template use File | Settings | File Templates.
 */
public class ProjectResourceManagerSession implements SessionBean {
    private static final Logger log = Logger.getLogger(ProjectResourceManagerSession.class);
    private SessionContext context;
    // ��������� ������ ��� �����������
    // ���������� �������, ������������������ � ���� ������
    private ResourceEntity resourceEntity = null;
    private ProjectEntity projectEntity = null;

    // �������� �� ���������
    public void ejbCreate() throws CreateException {
    }
// ����� ��� �������� ����� ������ �
// ������������ ���������� � �����������
// ����������� ���������� �������

    // ��� ������ �������� ���������� �����

    public void ejbCreate(String resourceId, String projectId) throws CreateException, ResourceException {
        try {
// ����� � ������� ���������� � ������������
            connectToEntities(resourceId, projectId);
        } catch (...){
// ��������� �������������� ��������
    }
    }

    /*����� ��� ��������� ���������� Session Facade
  � ��� ������������ ���������� ������� ��� ������
  �������� ���������� �����*/

    private void connectToEntities(String resourceId, String projectId) throws ResourceException {
        resourceEntity = getResourceEntity(resourceId);
        try {
            projectEntity = getProjectEntity(projectId);
        } catch (ProjectException e) {
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //log.error(e);
            throw new ResourceException(e);
        }
    }

    // ����� ��� ��������� ���������� Session Facade
    // � ��������� ������� ����������� ���������� �������

    // ��� ������ �������� ���������� �����

    public void resetEntities(String resourceId, String projectId) throws PSAException {
        connectToEntities(resourceId, projectId);
    }

    // private-����� ��� ��������� Home ��� Resource
    private ResourceEntityHome getResourceHome() throws ServiceLocatorException {
        return (ResourceEntityHome) ServiceLocator.getInstance().getHome(
                "ResourceEntity", ResourceEntityHome.class);
    }

    // private-����� ��� ��������� Home ��� Project
    private ProjectEntityHome getProjectHome() throws ServiceLocatorException {
        return (ProjectEntityHome) ServiceLocator.getInstance().getHome(
                "ProjectEntity", ProjectEntityHome.class);
    }

    // private-����� ��� ��������� Resource
    private ResourceEntity getResourceEntity(String resourceId) throws ResourceException {
        try {
            ResourceEntityHome home = getResourceHome();
            return (ResourceEntity) home.findByPrimaryKey(resourceId);

            // ��������� �������������� ��������
        } catch (ServiceLocatorException e) {
            //e.printStackTrace();
            // log.error(e);
            throw new ResourceException(e);
        }
    }

    // private-����� ��� ��������� Project
    private ProjectEntity getProjectEntity(String projectId) throws ProjectException {
// similar to getResourceEntity
        try {
            ProjectEntityHome home = getProjectHome();
            return (ProjectEntity) home.findByPrimaryKey(projectId);
        } catch (ServiceLocatorException e) {
            //e.printStackTrace();
            //log.error(e);
            throw new ProjectException(e);
        }
    }

/* ����� ��� ������������ �������� ��������
������������ � ���������� ������� �������.
�� ����� ���� � ������������ Project � Resource*/

    public void assignResourceToProject(int numHours) throws PSAException {
        try {
            if ((projectEntity == null) ||
                    (resourceEntity == null)) {
                // SessionFacade �� ��������� � ���������
                throw new PSAException("Not connected to Entity");
            }
            // �������� ������ Resource
            // Get Resource data
            ResourceVO resourceVO = resourceEntity.getResourceData();

            // Get Project data
            ProjectVO projectVO = projectEntity.getProjectData();
            // first add Resource to Project
            projectEntity.addResource(resourceVO);
            // Create a new Commitment for the Project
            CommitmentVO commitment = new CommitmentVO("����� ������");

            // add the commitment to the Resource
            projectEntity.addCommitment(commitment);

        } catch (...){
        // Handle exceptions
    }
    }

    // Similarly implement other business methods to
    // facilitate various use cases/interactions
    public void unassignResourceFromProject() throws PSAException {
        ...
    }

    // Methods working with ResourceEntity
    public ResourceVO getResourceData() throws ResourceException {
        ...
    }

    // Update Resource Entity Bean
    public void setResourceData(ResourceVO resource) throws ResourceException {
        ...
    }

    // Create new Resource Entity bean
    public ResourceVO createNewResource(ResourceVO resource) throws ResourceException {
        ...
    }

    // Methods for managing resource�s blockout time
    public void addBlockoutTime(Collection blockoutTime) throws RemoteException, BlockoutTimeException {
        ...
    }

    public void updateBlockoutTime(Collection blockoutTime) throws RemoteException, BlockoutTimeException {
        ...
    }

    public Collection getResourceCommitments() throws RemoteException, ResourceException {
        ...
    }

    // Methods working with ProjectEntity
    public ProjectVO getProjectData() throws ProjectException {
        ...
    }

    // Update Project Entity Bean
    public void setProjectData(ProjectVO project) throws ProjectException {
        ...
    }

    // Create new Project Entity bean
    public ProjectVO createNewProject(ProjectVO project) throws ProjectException {
        ...
    }


    // Other session facade method examples

    // This proxies a call to a Value Object Assembler
    // to obtain a composite value object.
    // See Value Object Assembler pattern

    public ProjectCVO getProjectDetailsData()
            throws PSAException {
        try {
            ProjectVOAHome projectVOAHome = (ProjectVOAHome)
                    ServiceLocator.getInstance().getHome(
                            "ProjectVOA", ProjectVOAHome.class);
            // Value Object Assembler session bean
            ProjectVOA projectVOA =
            projectVOAHome.create(...);
            return projectVOA.getData(...);
        } catch (...){
        // Handle / throw exceptions
    }
    }

    // These method proxies a call to a ValueListHandler
    // to get a list of projects. See Value List Handler
    // pattern.
    public Collection getProjectsList(Date start, Date end) throws PSAException {
        try {
            ProjectListHandlerHome projectVLHHome = (ProjectVLHHome) ServiceLocator.getInstance().getHome(
                    "ProjectListHandler", ProjectVLHHome.class);
            // Value List Handler session bean
            ProjectListHandler projectListHandler = projectVLHHome.create();
            return projectListHandler.getProjects(start, end);
        } catch (ServiceLocatorException e) {
            throw new PSAException("no find " + e);
            // Handle / throw exceptions
        }
    }


    public void ejbActivate() {
    }

    public void ejbPassivate() {
        context = null;
    }

    public void setSessionContext(SessionContext ctx) {
        this.context = ctx;
    }

    public void ejbRemove() {
    }
}
