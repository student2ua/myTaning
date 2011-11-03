package obuchenie.javaee.vo;

import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 20:25:35
 * Multiple Value Objects Strategy Ð ResourceVO
 */
public class ResourceVO implements Serializable {
    private static final Logger log = Logger.getLogger(ResourceVO.class);
    public String resourceId;
    public String lastName;
    public String firstName;
    public String department;
    public String grade;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
