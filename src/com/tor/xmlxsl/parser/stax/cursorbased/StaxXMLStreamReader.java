package com.tor.xmlxsl.parser.stax.cursorbased;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.01.13
 * Time: 14:58
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * Чтение через курсор
 * http://www.journaldev.com/1226/java-stax-cursor-based-api-read-xml-example
 * <p>
 * When we use StAX XML Parser, we need to create XMLInputFactory to read XML file. Then we can chose cursor based API
 * by creating XMLStreamReader object to read file. XMLStreamReader next() method is used to get the next parsing event
 * and it returns the int value depending on the event type. Common event types are Start Document, Start Element,
 * Characters, End Element and End Document. XMLStreamConstants contains int constants that can be used to process
 * events based on it’s type.</p>
 */
public class StaxXMLStreamReader {
    public static final String XML_FILE_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Employees>\n" +
            "    <Employee id=\"1\">\n" +
            "        <age>29</age>\n" +
            "        <name>Pankaj</name>\n" +
            "        <gender>Male</gender>\n" +
            "        <role>Java Developer</role>\n" +
            "    </Employee>\n" +
            "    <Employee id=\"2\">\n" +
            "        <age>35</age>\n" +
            "        <name>Lisa</name>\n" +
            "        <gender>Female</gender>\n" +
            "        <role>CEO</role>\n" +
            "    </Employee>\n" +
            "    <Employee id=\"3\">\n" +
            "        <age>40</age>\n" +
            "        <name>Tom</name>\n" +
            "        <gender>Male</gender>\n" +
            "        <role>Manager</role>\n" +
            "    </Employee>\n" +
            "    <Employee id=\"4\">\n" +
            "        <age>25</age>\n" +
            "        <name>Meghna</name>\n" +
            "        <gender>Female</gender>\n" +
            "        <role>Manager</role>\n" +
            "    </Employee>\n" +
            "</Employees>";

    public static void main(String[] args) {
        // List<EmployeeModel> modelList = parseXML("E:\\project\\MyProject\\treeTable\\src\\com\\tor\\xmlxsl\\parser\\stax\\cursorbased\\employee.xml"
        List<EmployeeModel> modelList = parseXML(XML_FILE_CONTENT
        );
        for (EmployeeModel model : modelList) {
            System.out.println("model = " + model.toString());
        }
    }

    private static List<EmployeeModel> parseXML(String filePath) {
        List<EmployeeModel> list = new LinkedList<EmployeeModel>();
        EmployeeModel model = null;
        boolean bName = false;
        boolean bAge = false;
        boolean bGender = false;
        boolean bRole = false;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            // XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(filePath));
            XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(filePath));
            int eventType = reader.getEventType();
            while (true) {
                switch (eventType) {
                    case XMLStreamReader.START_ELEMENT: {
                        if (reader.getLocalName().equals("Employee")) {
                            model = new EmployeeModel();
                            model.setId(Integer.parseInt(reader.getAttributeValue(0)));
                        }
                        if (reader.getLocalName().equals("name")) {
                            bName = true;
                        }
                        if (reader.getLocalName().equals("age")) {
                            bAge = true;
                        }
                        if (reader.getLocalName().equals("gender")) {
                            bGender = true;
                        }
                        if (reader.getLocalName().equals("role")) {
                            bRole = true;
                        }
                        break;
                    }
                    case XMLStreamConstants.CHARACTERS: {
                        if (bName) {
                            model.setName(reader.getText());
                            bName = false;
                        }
                        if (bAge) {
                            model.setAge(Integer.parseInt(reader.getText()));
                            bAge = false;
                        }
                        if (bGender) {
                            model.setGender(reader.getText());
                            bGender = false;
                        }
                        if (bRole) {
                            model.setRole(reader.getText());
                            bRole = false;
                        }
                        break;
                    }
                    case XMLStreamConstants.END_ELEMENT: {
                        if (reader.getLocalName().equals("Employee")) {
                            list.add(model);
                        }
                        break;
                    }
                }
                if (!reader.hasNext()) break;
                eventType = reader.next();
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } /*catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        return list;
    }
}
