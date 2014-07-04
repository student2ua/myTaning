package com.tor.messagebean;

import com.tor.entity.Book;
import com.tor.stateless.LibraryPersistentBeanRemote;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 14:00
 */
@MessageDriven(name = "BookMessageHandler",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/BookQueue")
        })
public class LibraryMessageBean implements MessageListener {
    @Resource
    private MessageDrivenContext context;
    @EJB
    LibraryPersistentBeanRemote libraryBean;

    public LibraryMessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = null;
        try {
            objectMessage = (ObjectMessage) message;
            Book book = (Book) objectMessage.getObject();
            libraryBean.addBook(book);
        } catch (JMSException e) {
            context.setRollbackOnly();
        }
    }
}
