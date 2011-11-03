package obuchenie.javaee.beans.projdetailassem;

import obuchenie.javaee.ServiceLocator;
import obuchenie.javaee.beans.projectentity.ProjectEntity;
import obuchenie.javaee.beans.projectentity.ProjectEntityHome;
import obuchenie.javaee.beans.resourceentity.ResourceEntity;
import obuchenie.javaee.beans.resourceentity.ResourceEntityHome;
import obuchenie.javaee.vo.ProjectVO;
import obuchenie.javaee.vo.ResourceVO;
import obuchenie.javaee.vo.TaskResourceVO;
import obuchenie.javaee.vo.TaskVO;
import org.apache.log4j.Logger;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 20:13:30
 * Implementing the Value Object Assembler
 */
public class ProjectDetailsAssembler implements javax.ejb.SessionBean {
    private static final Logger log = Logger.getLogger(ProjectDetailsAssembler.class);

    public ProjectDetailsData getData(String projectId) {

        // Construct the composite value object
        ProjectDetailsData pData = new
                ProjectDetailsData();

        //get the project details;
        ProjectHome projectHome =
                ServiceLocator.getInstance().getHome(
                        "Project", ProjectEntityHome.class);
        ProjectEntity project =
                projectHome.findByPrimaryKey(projectId);
        ProjectVO projVO = project.getData();

        // Add Project Info to ProjectDetailsData
        pData.projectData = projVO;

        //get the project manager details;
        ProjectManagerHome projectManagerHome =
                ServiceLocator.getInstance().getHome(
                        "ProjectManager", ProjectEntityHome.class);

        ProjectManagerEntity projectManager =
                projectManagerHome.findByPrimaryKey(
                        projVO.managerId);

        ProjectManagerVO projMgrVO =
                projectManager.getData();

        // Add ProjectManager info to ProjectDetailsData
        pData.projectManagerData = projMgrVO;

        // Get list of TaskVOs from the Project
        Collection projTaskList = project.getTasksList();

        // construct a list of TaskResourceVOs
        ArrayList listOfTasks = new ArrayList();

        Iterator taskIter = projTaskList.iterator();
        while (taskIter.hasNext()) {
            TaskVO task = (TaskVO) taskIter.next();

            //get the Resource details;
            ResourceHome resourceHome =
                    ServiceLocator.getInstance().getHome(
                            "Resource", ResourceEntityHome.class);

            ResourceEntity resource =
                    resourceHome.findByPrimaryKey(
                            task.assignedResourceId);

            ResourceVO resVO = resource.getResourceData();

            // construct a new TaskResourceVO using Task
            // and Resource data
            TaskResourceVO trVO = new TaskResourceVO(
                    task.projectId, task.taskId,
                    task.name, task.description,
                    task.startDate, task.endDate,
                    resVO);

            // add TaskResourceVO to the list
            listOfTasks.add(trVO);
        }

        // add list of tasks to ProjectDetailsData
        pData.listOfTasks = listOfTasks;

        // add any other data to the value object

        // return the composite value object
        return pData;

    }

    public void ejbActivate() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbPassivate() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbRemove() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
