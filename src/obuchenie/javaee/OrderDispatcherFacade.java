package obuchenie.javaee;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 19:17:39
 * Session Facade as Client for Service Activator
 */
public class OrderDispatcherFacade implements javax.ejb.SessionBean {

    // business method to create new Order
    public int createOrder(...) throws OrderException {

        // create new business order entity bean

        // successfully created Order. send Order to
        // asynchronous backend processing
        OrderSender orderSender = new OrderSender();
        orderSender.sendOrder(order);

        // close the sender, if done...
        orderSender.close();

        // other processing
    }
}
