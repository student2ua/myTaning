package obuchenie.javaee.beans.projectentity;

import obuchenie.javaee.vo.ProjectVO;
import obuchenie.javaee.vo.ResourceVO;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 20:19:43
 * Implementing the Value Object Pattern Ð Entity Bean Class
 * Implementing Updatable Value Objects Strategy
 */
public class ProjectEntity implements EntityBean {
    private EntityContext entityContext;
    public String projectId;
    public String projectName;
    public String managerId;
    public String customerId;
    public Date startDate;
    public Date endDate;
    public boolean started;
    public boolean completed;
    public boolean accepted;
    public Date acceptedDate;
    public String projectDescription;
    public String projectStatus;
    private boolean closed;

    // other attributes...
    private ArrayList commitments;

    // Method to get value object for Project data
    public ProjectVO getProjectData() {
        return createProjectVO();
    }

    // method to create a new value object and
    // copy data from entity bean into the value
    // object
    private ProjectVO createProjectVO() {
        ProjectVO proj = new ProjectVO();
        proj.projectId = projectId;
        proj.projectName = projectName;
        proj.managerId = managerId;
        proj.startDate = startDate;
        proj.endDate = endDate;
        proj.customerId = customerId;
        proj.projectDescription = projectDescription;
        proj.projectStatus = projectStatus;
        proj.started = started;
        proj.completed = completed;
        proj.accepted = accepted;
        proj.closed = closed;
        return proj;
    }

    // method to set entity values with a value object
    public void setProjectData(ProjectVO updatedProj) {
        mergeProjectData(updatedProj);
    }

    // method to merge values from the value object into
    // the entity bean attributes
    private void mergeProjectData(ProjectVO updatedProj) {
        // version control check may be necessary here
        // before merging changes in order to
        // prevent losing updates by other clients
        projectId = updatedProj.projectId;
        projectName = updatedProj.projectName;
        managerId = updatedProj.managerId;
        startDate = updatedProj.startDate;
        endDate = updatedProj.endDate;
        customerId = updatedProj.customerId;
        projectDescription = updatedProj.projectDescription;
        projectStatus = updatedProj.projectStatus;
        started = updatedProj.started;
        completed = updatedProj.completed;
        accepted = updatedProj.accepted;
        closed = updatedProj.closed;
    }


    public void setEntityContext(EntityContext entityContext) throws EJBException, RemoteException {
        this.entityContext = entityContext;
    }

    public void unsetEntityContext() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbRemove() throws RemoveException, EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbActivate() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbPassivate() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbLoad() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbStore() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addResource(ResourceVO resourceVO) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void addCommitment(CommitmentVO commitment) {
        this.commitments.add(commitment);
    }
}
