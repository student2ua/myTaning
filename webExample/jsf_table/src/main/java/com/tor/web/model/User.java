package com.tor.web.model;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 01.06.15
 * Time: 2:03
 * To change this template use File | Settings | File Templates.
 */
public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int age;

    public User(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
}
