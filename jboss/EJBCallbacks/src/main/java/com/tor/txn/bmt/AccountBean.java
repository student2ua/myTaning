package com.tor.txn.bmt;

import com.tor.txn.bmt.entity.Account;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.03.15
 * Time: 20:15
 * In Bean Managed Transactions, Transactions can be managed by handling exceptions at application level. Following are the key points to be considered
 * <p/>
 * Start - When to start a transaction in a business method.
 * <p/>
 * Sucess - Identify success scenario when a transaction is to be committed.
 * <p/>
 * Failed - Identify failure scenario when a transaction is to be rollback.
 */
@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AccountBean implements AccountBeanLocal {
    @Resource
    private UserTransaction userTransaction;

    public void transferFund(Account fromAccount, double fund,
                             Account toAccount) throws Exception {
        try {
            userTransaction.begin();
            confirmAccountDetail(fromAccount);
            withdrawAmount(fromAccount, fund);
            confirmAccountDetail(toAccount);
            depositAmount(toAccount, fund);
            userTransaction.commit();
        } catch (NotSupportedException e) {
            e.printStackTrace();
            userTransaction.rollback();
        } catch (SystemException e) {
            e.printStackTrace();
            userTransaction.rollback();
        }
    }

    private void depositAmount(Account account, double fund) throws Exception
//            throws InvalidAccountException
    {
    }

    private void withdrawAmount(Account account, double fund) throws Exception
//            throws InsufficientFundException
    {
    }

    private void confirmAccountDetail(Account account) throws Exception
//            throws PaymentException
    {
    }
}
