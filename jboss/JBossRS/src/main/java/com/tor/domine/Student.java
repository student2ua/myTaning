package com.tor.domine;

/**
 * User: tor
 * Date: 16.12.13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class Student {
    private Integer id;

    private String firstName;
    private String lastName;
    private String middleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
