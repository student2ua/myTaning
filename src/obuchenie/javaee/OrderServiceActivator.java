package obuchenie.javaee;

import javax.jms.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.04.2010
 * Time: 19:13:17
 * To change this template use File | Settings | File Templates.
 */
public class OrderServiceActivator implements javax.jms.MessageListener {

    // Queue session and receiver: see JMS API for
    // details
    private QueueSession orderQueueSession;
    private QueueReceiver orderQueueReceiver;

    // Note: values should come from property files or
    // environment instead of hard coding.
    private String connFactoryName = "PendingOrdersQueueFactory";
    private String queueName = "PendingOrders";

    // use a service locator to locate administered
    // JMS components such as a Queue or a Queue
    // Connection factory
    private JMSServiceLocator serviceLocator;

    public OrderServiceActivator(String connFactoryName, String queueName) {
        super();
        this.connFactoryName = connFactoryName;
        this.queueName = queueName;
        startListener();
    }

    private void startListener() {
        try {
            serviceLocator = new JMSServiceLocator(connFactoryName);
            qConnFactory = serviceLocator.getQueueConnectionFactory();
            qConn = qConnFactory.createQueueConnection();

            // See JMS API for method usage and arguments
            orderQueueSession = qConn.createQueueSession();
            Queue ordersQueue = serviceLocator.getQueue(queueName);
            orderQueueReceiver = orderQueueSession.createReceiver(ordersQueue);
            orderQueueReceiver.setMessageListener(this);
        }
        catch (JMSException excp) {
            // handle error
        }
    }

    // The JMS API specifies the onMessage method in the
    // javax.jms.MessageListener interface.
    // This method is asynchronously invoked
    // when a message arrives on the Queue being
    // listened to by the ServiceActivator.
    // See JMS Specification and API for more details.
    public void onMessage(Message msg) {
        try {
            // parse Message msg. See JMS API for Message.

            // Invoke business method on an enterprise
            // bean using the bean’s business delegate.
            // OrderProcessorDelegate is the business
            // delegate for OrderProcessor Session bean.
            // See Business Delegate pattern for details.
            OrderProcessorDelegate orderProcDeleg = new OrderProcessorDelegate();

            // Use data values from the parsed message to
            // invoke business method on bean via delegate
            orderProcDeleg.fulfillOrder();

            // send any acknowledgement here...
        }
        catch (JMSException jmsexcp) {
            // Handle JMSExceptions, if any
        }
        catch (Exception excp) {
            // Handle any other exceptions
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
