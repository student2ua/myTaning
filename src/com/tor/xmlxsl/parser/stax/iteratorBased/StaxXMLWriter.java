package com.tor.xmlxsl.parser.stax.iteratorBased;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.01.13
 * Time: 16:21
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.journaldev.com/892/how-to-write-xml-file-in-java-using-java-stax-api
 */
public class StaxXMLWriter {
    public static void main(String[] args) {
        String failPath = "E:\\project\\MyProject\\treeTable\\src\\com\\tor\\xmlxsl\\parser\\stax\\cursorbased\\employee2.xml";
        String rootElement = "Employee";
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "Pingwin");
        map.put("age", "1");
        map.put("role", "lol");
        map.put("gender", "0");
        StaxXMLWriter xmlWriter = new StaxXMLWriter();
        xmlWriter.writeXML(failPath, rootElement, map);
    }

    private void writeXML(String failPath, String rootElement, Map<String, String> map) {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        StringWriter stringWriter=new StringWriter();
        try {
//            XMLEventWriter eventWriter = xmlOutputFactory.createXMLEventWriter(new FileOutputStream(failPath), "UTF-8");
            XMLEventWriter eventWriter = xmlOutputFactory.createXMLEventWriter(stringWriter);
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);
            StartElement startElement = eventFactory.createStartElement("", "", rootElement);
            eventWriter.add(startElement);
            eventWriter.add(end);
            for (String sPk : map.keySet()) {
                createNode(eventWriter, sPk, map.get(sPk));
            }
            eventWriter.add(eventFactory.createEndElement("", "", rootElement));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.add(end);

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } /*catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/
        System.out.println("stringWriter = " + stringWriter);
    }

    private void createNode(XMLEventWriter eventWriter, String sPk, String value) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        StartElement startElement = eventFactory.createStartElement("", "", sPk);
        eventWriter.add(tab);
        eventWriter.add(startElement);

        Characters character = eventFactory.createCharacters(value);
        eventWriter.add(character);

        EndElement endElement = eventFactory.createEndElement("", "", sPk);
        eventWriter.add(endElement);
        eventWriter.add(end);
    }
}
