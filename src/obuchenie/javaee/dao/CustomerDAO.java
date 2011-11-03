package obuchenie.javaee.dao;

import obuchenie.javaee.vo.Customer;

import javax.sql.RowSet;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 18:57:00
 * Interface that all CustomerDAOs must support.
 */
public interface CustomerDAO {
  public int insertCustomer(Customer customer);
  public boolean deleteCustomer();
  public Customer findCustomer();
  public boolean updateCustomer();
  public RowSet selectCustomersRS();
  public Collection selectCustomersVO();
}
