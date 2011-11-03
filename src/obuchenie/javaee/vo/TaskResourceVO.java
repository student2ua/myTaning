package obuchenie.javaee.vo;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 20:09:55
 * To change this template use File | Settings | File Templates.
 */
public class TaskResourceVO {
    private static final Logger log = Logger.getLogger(TaskResourceVO.class);
    public String projectId;
    public String taskId;
    public String name;
    public String description;
    public Date startDate;
    public Date endDate;
    public ResourceVO assignedResource;

    public TaskResourceVO(String projectId, String taskId, String name, String description, Date startDate, Date endDate, ResourceVO assignedResource) {
        this.projectId = projectId;
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignedResource = assignedResource;
    }
}
