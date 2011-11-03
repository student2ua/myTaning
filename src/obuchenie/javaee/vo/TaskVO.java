package obuchenie.javaee.vo;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 20:11:05
 * To change this template use File | Settings | File Templates.
 */
public class TaskVO {
    private static final Logger log = Logger.getLogger(TaskVO.class);
    public String projectId;
    public String taskId;
    public String name;
    public String description;
    public Date startDate;
    public Date endDate;
    public String assignedResourceId;

    public TaskVO(String projectId, String taskId, String name, String description, Date startDate, Date endDate, String assignedResourceId) {
        this.projectId = projectId;
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignedResourceId = assignedResourceId;
    }
}
