package com.tor.web;

import com.tor.web.model.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 01.06.15
 * Time: 2:05
 * JSF dataTable example
 */
@ManagedBean
@RequestScoped
public class UsersBean {
    private List<User> userList;

    @PostConstruct
    private void init() {
        userList = new ArrayList<User>();
        userList.add(new User("John", "Doe", "john.doe@example.com", 32));
        userList.add(new User("Peter", "Smith", "peter.smith@example.com", 25));
        userList.add(new User("Mary", "Jane", "mary.jane@example.com", 27));
        userList.add(new User("Mike", "Skeet", "mike.skeet@example.com", 35));
    }

    public List<User> getUserList() {
        return userList;
    }

    public int getUserListSize() {
        return getUserList().size();
    }
}
