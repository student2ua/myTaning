package com.tor.jpa;

import javax.persistence.*;

/**
 * User: tor
 * Date: 08.07.14
 * Time: 16:18
 * http://devcolibri.com/3575
 */
@Entity
@Table(schema = "human", name = "human")
@NamedQuery(name = "Human.getAll", query = "select h from Human h")
public class Human {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;

    public Human() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
