package com.tor.jms;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.*;

/**
 * User: Admin
 * Date: 14.04.19
 * Time: 17:04
 * https://habr.com/ru/post/184460/
 */
@ApplicationScoped
public class Sender {
    //    @Resource(mappedName="java:/queue/test")
    @Resource(mappedName = "jms/queue/test")
    private Queue queue;
    //    @Inject
//    private javax.jms.JMSContext context;
    @Resource(mappedName = "ConnectionFactory")
    private QueueConnectionFactory connectionFactory;

    public void sendMessage(String txt) {
        sendMessageJMS11(connectionFactory, queue, txt);
    }

    public void sendMessageJMS11(ConnectionFactory connectionFactory, Queue queue, String text) {
        try {
            Connection connection = connectionFactory.createConnection();
            try {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer messageProducer = session.createProducer(queue);
                TextMessage textMessage = session.createTextMessage(text);
                messageProducer.send(textMessage);
            } finally {
                connection.close();
            }
        } catch (JMSException ex) {
            // handle exception (details omitted)
        }
    }

/*    public void sendMessageJMS20(ConnectionFactory connectionFactory, Queue queue, String text) {
        try (JMSContext context = connectionFactory.createContext();){
            context.createProducer().send(queue, text);
        } catch (JMSRuntimeException ex) {
            // handle exception (details omitted)
        }
    }
     public void sendMessage(String txt) {
        context.createProducer().send(queue, txt);
    }

    */

}
