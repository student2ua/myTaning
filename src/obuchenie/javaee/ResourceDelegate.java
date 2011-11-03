package obuchenie.javaee;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 20:04:24
 * Implementing Business Delegate Pattern
 */
public class ResourceDelegate {
    private static final Logger log = Logger.getLogger(ResourceDelegate.class);
    // Remote reference for Session Facade
  private ResourceSession session;

  // Class for Session Facade's Home object
  private static final Class homeClazz =   corepatterns.apps.psa.ejb.ResourceSessionHome.class;

  // Default Constructor. Looks up home and connects
  // to session by creating a new one
  public ResourceDelegate() throws ResourceException {
    try {
      ResourceSessionHome home = (ResourceSessionHome)
        ServiceLocator.getInstance().getHome(
          "Resource", homeClazz);
      session = home.create();
    } catch(ServiceLocatorException ex) {
      // Translate Service Locator exception into
      // application exception
      throw new ResourceException(...);
    } catch(CreateException ex) {
      // Translate the Session create exception into
      // application exception
      throw new ResourceException(...);
    } catch(RemoteException ex) {
      // Translate the Remote exception into
      // application exception
      throw new ResourceException(...);
    }
  }

  // Constructor that accepts an ID (Handle id) and
  // reconnects to the prior session bean instead
  // of creating a new one
  public BusinessDelegate(String id)
    throws ResourceException {
    super();
    reconnect(id);
  }

  // Returns a String ID the client can use at a
  // later time to reconnect to the session bean
  public String getID() {
    try {
      return ServiceLocator.getId(session);
    } catch (Exception e) {
      // Throw an application exception
    }
 }

  // method to reconnect using String ID
  public void reconnect(String id)
    throws ResourceException {
    try {
      session = (ResourceSession)
                ServiceLocator.getService(id);
    } catch (RemoteException ex) {
      // Translate the Remote exception into
      // application exception
      throw new ResourceException(...);
    }
  }

  // The following are the business methods
  // proxied to the Session Facade. If any service
  // exception is encountered, these methods convert
  // them into application exceptions such as
  // ResourceException, SkillSetException, and so
  // forth.

  public ResourceVO setCurrentResource(
    String resourceId)
    throws ResourceException {
    try {
      return session.setCurrentResource(resourceId);
    } catch (RemoteException ex) {
      // Translate the service exception into
      // application exception
      throw new ResourceException(...);
    }
  }

  public ResourceVO getResourceDetails()
    throws ResourceException {

    try {
      return session.getResourceDetails();
    } catch(RemoteException ex) {
      // Translate the service exception into
      // application exception
      throw new ResourceException(...);
    }
  }

  public void setResourceDetails(ResourceVO vo)
    throws ResourceException {
    try {
      session.setResourceDetails(vo);
    } catch(RemoteException ex) {
      throw new ResourceException(...);
    }
  }

  public void addNewResource(ResourceVO vo)
    throws ResourceException {
    try {
      session.addResource(vo);
    } catch(RemoteException ex) {
      throw new ResourceException(...);
    }
  }

  // all other proxy method to session bean
  
}
