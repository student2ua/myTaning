package obuchenie.javaee.vo;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 17:36:29
 * To change this template use File | Settings | File Templates.
 */
public interface Customer extends Serializable {
    
    public String getCustomerName();

    public String getCustomerAddress();

    public void setCustomerName(String customerName);

    public void setCustomerAddress(String customerAddress);
}
