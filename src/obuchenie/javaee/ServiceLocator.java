package obuchenie.javaee;

import obuchenie.javaee.exceptions.ServiceLocatorException;
import org.apache.log4j.Logger;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.io.*;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 26.04.2010
 * Time: 20:03:29
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLocator {
    private static final Logger log = Logger.getLogger(ServiceLocator.class);
    public static ServiceLocator me;
    InitialContext context = null;

    private ServiceLocator() throws ServiceLocatorException {
        try {
            context = new InitialContext();
        } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
        }
    }

    // Возвращает экземпляр класса ServiceLocator
    public static ServiceLocator getInstance() throws ServiceLocatorException {
        if (me == null) {
            me = new ServiceLocator();
        }
        return me;
    }

    // Преобразует сериализованную строку в EJBHandle
    // затем в EJBObject.
    public EJBObject getService(String id) throws ServiceLocatorException {
        if (id == null) {
            throw new ServiceLocatorException(" getService(id) id=null");
        }
        try {
            byte[] bytes = new String(id).getBytes();
            InputStream io = new ByteArrayInputStream(bytes);
            ObjectInputStream os = new ObjectInputStream(io);
            javax.ejb.Handle handle = (javax.ejb.Handle) os.readObject();
            return handle.getEJBObject();
        }
        catch (Exception ex) {
            throw new ServiceLocatorException(ex);
        }
    }

    // Возвращает объект String, представляющий идентификатор
    // данного EJBObject в сериализованном формате.
    protected String getId(EJBObject session) throws ServiceLocatorException {
        try {
            javax.ejb.Handle handle = session.getHandle();
            ByteArrayOutputStream fo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(fo);
            so.writeObject(handle);
            so.flush();
            so.close();
            return new String(fo.toByteArray());
        } catch (RemoteException ex) {
            throw new ServiceLocatorException(ex);
        } catch (IOException ex) {
            throw new ServiceLocatorException(ex);
        }

    }

    // Возвращает объект EJBHome запрошенного имени
    // службы. Генерирует ServiceLocatorException при
    // возникновении любой ошибки при поиске

    public EJBHome getHome(String name, Class clazz)
            throws ServiceLocatorException {
        try {
            Object objref = context.lookup(name);
            EJBHome home = (EJBHome) PortableRemoteObject.narrow(objref, clazz);
            return home;
        } catch (NamingException ex) {
            throw new ServiceLocatorException(ex);
        }
    }
}