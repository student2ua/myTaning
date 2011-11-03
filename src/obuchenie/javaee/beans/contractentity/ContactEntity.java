package obuchenie.javaee.beans.contractentity;

import obuchenie.javaee.vo.ContactVO;
import org.apache.log4j.Logger;

import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.EntityContext;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 16:55:20
 * To change this template use File | Settings | File Templates.
 */
public class ContactEntity extends ContactVO implements javax.ejb.EntityBean {
    private static final Logger log = Logger.getLogger(ContactEntity.class);
    // the client calls the getData method
    // on the ContactEntity bean instance.
    // getData() is inherited from the value object
    // and returns the ContactVO value object

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
