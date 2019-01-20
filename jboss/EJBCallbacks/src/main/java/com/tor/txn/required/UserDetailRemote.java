package com.tor.txn.required;

import javax.ejb.Remote;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.03.15
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
@Remote
public interface UserDetailRemote {
   public void createUserDetail();
}
