package com.tor.txn.required;

import com.tor.txn.required.entity.User;

import javax.ejb.EJB;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.03.15
 * Time: 19:49
 * createUser() business method is using createUserDetail().
 * If exception occured during createUser() call and User object is not created then UserDetail object will also not be created.
 */
public class UserSessionBean implements UserRemote {
    private User user;
    @EJB
    private UserDetailRemote userDetail;

    public void createUser() {
        //create user
        //...
        //create user details
        userDetail.createUserDetail();
    }
}
