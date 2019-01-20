package com.tor.txn.bmt;

import com.tor.txn.bmt.entity.Account;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.03.15
 * Time: 20:15
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface AccountBeanLocal {
    public void transferFund(Account fromAccount, double fund ,
                             Account toAccount) throws Exception;
}
