package com.tor.txn.required;

import com.tor.txn.required.entity.UserDetail;

import javax.ejb.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.03.15
 * Time: 19:41
 * Container Managed Transactions - In this type, container manages the transaction states.
 * Bean Managed Transactions - In this type, developer manages the life cycle of transaction states.
 * <p/>
 * EJB 3.0 has specified following attributes of transactions which EJB containers implement.
 * <p/>
 * REQUIRED - Indicates that business method has to be executed within transaction otherwise a new transaction will be started for that method.
 * <p/>
 * REQUIRES_NEW - Indicates that a new transaction is to be started for the business method.
 * <p/>
 * SUPPORTS - Indicates that business method will execute as part of transaction.
 * <p/>
 * NOT_SUPPORTED - Indicates that business method should not be executed as part of transaction.
 * <p/>
 * MANDATORY - Indicates that business method will execute as part of transaction otherwise exception will be thrown.
 * <p/>
 * NEVER - Indicates if business method executes as part of transaction then an exception will be thrown.
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserDetailBean implements UserDetailRemote {
    private UserDetail detail;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createUserDetail() {
        //create user details object
    }
}
