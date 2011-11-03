package obuchenie.javaee.vo;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 17:31:00
 * To change this template use File | Settings | File Templates.
 */
public interface Contact extends Serializable {
    
    public String getFirstName();

    public String getLastName();

    public String getContactAddress();

    public void setFirstName(String firstName);

    public void setLastName(String lastName);

    public void setContactAddress(String address);
}
