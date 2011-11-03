package obuchenie.javaee;

import obuchenie.javaee.dao.CustomerDAO;
import obuchenie.javaee.dao.DAOCloudscapeImpl.CloudscapeCustomerDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 18:41:30
 * Concrete DAOFactory Implementation for Cloudscape.
 */
public class CloudscapeDAOFactory extends DAOFactory {
    private static final Logger log = Logger.getLogger(CloudscapeDAOFactory.class);
    public static final String DRIVER = "COM.cloudscape.core.RmiJdbcDriver";
    public static final String DBURL = "jdbc:cloudscape:rmi://localhost:1099/CoreJ2EEDB";

    // method to create Cloudscape connections
    public static Connection createConnection() {
        // Use DRIVER and DBURL to create a connection
        // Recommend connection pool implementation/usage

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(DBURL);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            log.error(e);
        } catch (SQLException e) {
            //e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    public CustomerDAO getCustomerDAO() {
        // CloudscapeCustomerDAO implements CustomerDAO
        return new CloudscapeCustomerDAO();
    }

    public AccountDAO getAccountDAO() {
        // CloudscapeAccountDAO implements AccountDAO
        return new CloudscapeAccountDAO();
    }

    public OrderDAO getOrderDAO() {
        // CloudscapeOrderDAO implements OrderDAO
        return new CloudscapeOrderDAO();
    }
}
