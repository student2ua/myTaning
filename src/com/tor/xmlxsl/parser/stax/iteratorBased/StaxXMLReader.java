package com.tor.xmlxsl.parser.stax.iteratorBased;

import com.tor.xmlxsl.parser.stax.cursorbased.EmployeeModel;
import com.tor.xmlxsl.parser.stax.cursorbased.StaxXMLStreamReader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.01.13
 * Time: 15:48
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.journaldev.com/1191/how-to-read-xml-file-in-java-using-java-stax-api
 */
public class StaxXMLReader {
    public static void main(String[] args) {
        //    List<EmployeeModel> modelList = parseXML("E:\\project\\MyProject\\treeTable\\src\\com\\tor\\xmlxsl\\parser\\stax\\cursorbased\\employee.xml"
        List<EmployeeModel> modelList = parseXML(StaxXMLStreamReader.XML_FILE_CONTENT
                //this.getClass().getResource("employee.xml")
        );
        for (EmployeeModel model : modelList) {
            System.out.println("model = " + model.toString());
        }
    }

    private static List<EmployeeModel> parseXML(String filePath) {
        List<EmployeeModel> list = new LinkedList<EmployeeModel>();
        EmployeeModel model = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
//            XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(filePath));
            XMLEventReader reader = factory.createXMLEventReader(new StringReader(filePath));
            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    //атрибуты - по имени
                    if (startElement.getName().getLocalPart().equals("Employee")) {
                        model = new EmployeeModel();
                        Attribute attribute = startElement.getAttributeByName(new QName("id"));
                        if (attribute != null) model.setId(Integer.parseInt(attribute.getValue()));
                    }
                    //другие элементы
                    else if (startElement.getName().getLocalPart().equals("name")) {
                        xmlEvent = reader.nextEvent();
                        model.setName(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("age")) {
                        xmlEvent = reader.nextEvent();
                        model.setAge(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("gender")) {
                        xmlEvent = reader.nextEvent();
                        model.setGender(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("role")) {
                        xmlEvent = reader.nextEvent();
                        model.setRole(xmlEvent.asCharacters().getData());
                    }
                } else if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Employee")) {
                        list.add(model);
                    }
                }

            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } /*catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/
        return list;
    }


}
