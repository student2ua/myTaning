package com.tor.web;

import javax.servlet.ServletException;
import javax.xml.messaging.JAXMServlet;
import javax.xml.messaging.ReqRespListener;
import javax.xml.soap.*;
import java.util.Iterator;

/**
 * User: tor
 * Date: 06.07.15
 * Time: 17:55
 * http://java.happycodings.com/servlets/code6.html
 * Servlet that accepts a SOAP message and looks through
 * its attachments before sending the SOAP part of the message
 * to the console and sending back a response
 */
public class AttachmentReceiver extends JAXMServlet implements ReqRespListener {
    private MessageFactory factory;

    @Override
    public void init() throws ServletException {
        try {
            factory = MessageFactory.newInstance();
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    // This is the application code for handling the message.. Once the
    // message is received the application can retrieve the soap part, the
    // attachment part if there are any, or any other information from the
    // message.
    @Override
    public SOAPMessage onMessage(SOAPMessage soapMessage) {
        try {
            System.out.println("soapMessage.countAttachments() = " + soapMessage.countAttachments());
            int i = 0;
            for (Iterator it = soapMessage.getAttachments(); it.hasNext(); i++) {
                AttachmentPart ap = (AttachmentPart) it.next();
                System.out.println(i + " getContentType() = " + ap.getContentType());
                SOAPPart part = soapMessage.getSOAPPart();
                System.out.println("SOAPPart = " + part);
            }

            SOAPMessage msg = factory.createMessage();
            SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
            env.getBody()
                    .addChildElement(env.createName("MessageResponse"))
                    .addTextNode("Right back at you");
            return msg;
        } catch (SOAPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }

    }
}