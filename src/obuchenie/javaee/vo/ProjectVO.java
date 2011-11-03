package obuchenie.javaee.vo;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 20:16:52
 * Implementing the Value Object Pattern Ð Value Object Class
 */
public class ProjectVO implements java.io.Serializable {
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

    public boolean closed;

    // Value object constructors...

    public ProjectVO(String projectId) {
        this.projectId = projectId;
    }

    public ProjectVO() {
    }
}
