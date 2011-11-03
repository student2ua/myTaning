package obuchenie.javaee.vo;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 16:47:19
 * Entity Inherits Value Object Strategy Ð Value Object  Class
 */
public class ContactVO implements Contact {
    private static final Logger log = Logger.getLogger(ContactVO.class);
    // public members
    public String firstName;
    public String lastName;
    public String address;

    // default constructor
    public ContactVO() {
    }

    // constructor accepting all values
    public ContactVO(String firstName, String lastName, String address) {
        init(firstName, lastName, address);
    }

    // constructor to create a new VO based
    // using an existing VO instance
    public ContactVO(ContactVO contact) {
        init(contact.firstName, contact.lastName, contact.address);
    }

    // method to set all the values
    public void init(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    // create a new value object 
    public ContactVO getData() {
        return new ContactVO(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContactAddress(String address) {
        this.address = address;
    }
}
