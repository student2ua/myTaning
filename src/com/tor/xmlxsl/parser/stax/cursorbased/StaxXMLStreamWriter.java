package com.tor.xmlxsl.parser.stax.cursorbased;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.01.13
 * Time: 19:04
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.journaldev.com/1230/java-stax-cursor-api-write-xml-file-example
 */
public class StaxXMLStreamWriter {
    public static void main(String[] args) {
        String rootElement = "Employee";
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "42");
        map.put("name", "Pingwin");
        map.put("age", "1");
        map.put("role", "lol");
        map.put("gender", "0");
        StaxXMLStreamWriter xmlWriter = new StaxXMLStreamWriter();
        StringWriter writer = new StringWriter();
        xmlWriter.writeXML(writer, rootElement, map);
        System.out.println("writer:\n" + writer);
    }

    private void writeXML(Writer writer, String rootElement, Map<String, String> map) {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        try {
            XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(writer);

            streamWriter.writeStartDocument("UTF-8", "1.0");
            streamWriter.writeCharacters("\n");
            streamWriter.writeStartElement(rootElement);

            streamWriter.writeAttribute("id", map.get("id"));

            for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            //id is attribute
                if (!stringStringEntry.getKey().equalsIgnoreCase("id")) {
                    createNode(stringStringEntry, streamWriter);
                }
            }
            streamWriter.writeCharacters("\n");
            streamWriter.writeEndElement();

            streamWriter.writeEndDocument();

            streamWriter.flush();
            streamWriter.close();

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void createNode(Map.Entry<String, String> mapE, XMLStreamWriter streamWriter) throws XMLStreamException {
        streamWriter.writeCharacters("\n\t");
        streamWriter.writeStartElement(mapE.getKey());
        streamWriter.writeCharacters(mapE.getValue());
        streamWriter.writeEndElement();
    }
}
