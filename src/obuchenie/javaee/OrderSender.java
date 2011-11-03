package obuchenie.javaee;

import org.apache.log4j.Logger;

import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 19:20:12
 * OrderSender: Used to Dispatch Orders to Queue
 */
public class OrderSender {
    private static final Logger log = Logger.getLogger(OrderSender.class);
    // Queue session and sender: see JMS API for details
    private QueueSession orderQueueSession;
    private QueueSender orderQueueSender;

    // These values could come from some property files
    private String connFactoryName ="PendingOrdersQueueFactory";
    private String queueName = "PendingOrders";

    // use a service locator to locate administered
    // JMS components such as a Queue or a Queue.
    // Connection factory
    private JMSServiceLocator serviceLocator;

    // method to initialize and create queue sender
    private void createSender() {
        try {
            // using ServiceLocator and getting Queue
            // Connection Factory is similar to the
            // Service Activator code.
            serviceLocator = new JMSServiceLocator(connFactoryName);
            qConnFactory = serviceLocator.getQueueConnectionFactory();
            qConn = qConnFactory.createQueueConnection();

            // See JMS API for method usage and arguments
            orderQueueSession = qConn.createQueueSession();
            Queue ordersQueue = serviceLocator.getQueue(queueName);
            orderQueueSender = orderQueueSession.createSender(ordersQueue);
        } catch (Exception excp) {
            // Handle exception - Failure to create sender
        }
    }

    // method to dispatch order to fulfillment service
    // for asynchronous processing
    public void sendOrder(Order newOrder) {
        try {
            // create a new Message to send Order object
            ObjectMessage objMessage = queueSession.createObjectMessage(order);

            // set object message properties and delivery
            // mode as required.
            // See JMS API for ObjectMessage

            // Set the Order into the object message
            objMessage.setObject(order);

            // send the message to the Queue
            orderQueueSender.send(objMessage);

        } catch (Exception e) {
            // Handle exceptions
        }
    }


    public void close() {
        try {
            // cleanup before closing
            orderQueueReceiver.setMessageListener(null);
            orderQueueSession.close();
        }
        catch (Exception excp) {
            // Handle exception - Failure to close
        }
    }
}
