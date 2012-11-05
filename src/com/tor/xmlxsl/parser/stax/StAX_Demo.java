package com.tor.xmlxsl.parser.stax;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.11.12
 * Time: 13:03
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.ibm.com/developerworks/ru/library/j-5things12/index.html?ca=drs-
 * http://www.xml.com/pub/a/2003/09/17/stax.html?page=2
 * скормить Ant config
 */
public class StAX_Demo {

    public static void main(String[] args) {
        for (String arg : args) {
            try {
                XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
                xmlInputFactory.setXMLReporter(new XMLReporter() {
                    @Override
                    public void report(String message, String errorType, Object relatedInformation, Location location) throws XMLStreamException {
                        System.err.println("Problem in " + location);
                        System.err.println("at line " + location.getLineNumber() + ", column " + location.getColumnNumber());
                        System.err.println(message);
                        System.err.println("errorType = " + errorType);
                        System.err.println("relatedInformation = " + relatedInformation);

                    }
                });
                XMLEventReader eventReader = xmlInputFactory.createXMLEventReader(new FileReader(arg));
                while (eventReader.hasNext()) {
                    XMLEvent event = (XMLEvent) eventReader.next();
                    switch (event.getEventType()) {
                        case XMLEvent.START_DOCUMENT: {
                            StartElement startElement = event.asStartElement();
                            if (startElement.getName().getLocalPart().equals("target")) {
                                Attribute attribute = startElement.getAttributeByName(new QName("name"));
                                System.out.println(attribute.getValue());
                            }
                            break;
                        }
                        //  case XMLEvent.COMMENT:{ event.}
                    }
                }


            } catch (XMLStreamException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
