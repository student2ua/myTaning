package obuchenie.javaee.dao.DAOCloudscapeImpl;

import obuchenie.javaee.dao.CustomerDAO;
import obuchenie.javaee.vo.Customer;
import org.apache.log4j.Logger;

import javax.sql.RowSet;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 19:02:10
 * CloudscapeCustomerDAO implementation of the CustomerDAO interface. This class can contain all Cloudscape
 * specific code and SQL statements.
 * The client is thus shielded from knowing these implementation details.
 */
public class CloudscapeCustomerDAO implements CustomerDAO {
    private static final Logger log = Logger.getLogger(CloudscapeCustomerDAO.class);

    public CloudscapeCustomerDAO() {
        // initialization
    }

    // The following methods can use
    // CloudscapeDAOFactory.createConnection()
    // to get a connection as required

    public int insertCustomer(Customer customer) {
        // Implement insert customer here.
        // Return newly created customer number
        // or a -1 on error
        return -1;
    }

    public boolean deleteCustomer() {
        // Implement delete customer here
        // Return true on success, false on failure
        return false;
    }

    public Customer findCustomer() {
        // Implement find a customer here using supplied
        // argument values as search criteria
        // Return a value object if found,
        // return null on error or if not found
        return null;
    }

    public boolean updateCustomer() {
        // implement update record here using data
        // from the customerData value object
        // Return true on success, false on failure or
        // error
        return false;
    }

    public RowSet selectCustomersRS() {
        // implement search customers here using the
        // supplied criteria.
        // Return a RowSet.
        return null;
    }

    public Collection selectCustomersVO() {
        // implement search customers here using the
        // supplied criteria.
        // Alternatively, implement to return a Collection
        // of value objects.
        return null;
    }
}
