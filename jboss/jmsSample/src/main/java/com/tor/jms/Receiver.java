package com.tor.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: Admin
 * Date: 14.04.19
 * Time: 17:23
 */
@MessageDriven(name = "Receiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/test"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class Receiver implements MessageListener {
    private Logger LOG = Logger.getLogger(Receiver.class.getName());

    public static List<String> messages = new ArrayList<String>();

    @Override
    public void onMessage(Message message) {
        try {
//            messages.add(message.getBody(String.class));
            String txt = null;
            if (message instanceof TextMessage) {
                txt = ((TextMessage) message).getText();
            } else txt = message.toString();
            messages.add(txt);
        } catch (JMSException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
