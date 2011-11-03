package obuchenie.javaee.beans;

import obuchenie.javaee.ValueObjectFactory;
import obuchenie.javaee.vo.ContactVO;
import obuchenie.javaee.vo.CustomerContactVO;
import obuchenie.javaee.vo.CustomerVO;
import org.apache.log4j.Logger;

import javax.ejb.EJBException;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 17:48:42
 * Value Object Factory Strategy Ð Entity Bean Class
 */
public class CustomerContactEntity extends CustomerContactVO implements javax.ejb.EntityBean {
    private static final Logger log = Logger.getLogger(CustomerContactEntity.class);
    // define constant to hold class name
    // complete value object. This is required by
    // the ValueObjectFactory.createValueObject(...)
    public static final String COMPLETE_VO_CLASSNAME = "CustomerContactVO";

    // method to return CustomerContactVO value object
    public CustomerContactVO getCustomerContact() {
        return (CustomerContactVO) ValueObjectFactory.createValueObject(this,
                "CustomerContactVO", COMPLETE_VO_CLASSNAME);
    }

    // method to return CustomerVO value object
    public CustomerVO getCustomer() {
        return (CustomerVO) ValueObjectFactory.createValueObject(this,
                "CustomerVO", COMPLETE_VO_CLASSNAME);
    }

    // method to return ContactVO value object
    public ContactVO getContact() {
        return (ContactVO) ValueObjectFactory.createValueObject(this,
                "ContactVO", COMPLETE_VO_CLASSNAME);
    }

    // other entity bean business methods

    // implement other entity bean methods...not shown

    public void ejbActivate() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbLoad() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbPassivate() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbRemove() throws RemoveException, EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbStore() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void unsetEntityContext() throws EJBException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
