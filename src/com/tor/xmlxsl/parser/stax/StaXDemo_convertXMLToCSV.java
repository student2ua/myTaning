package com.tor.xmlxsl.parser.stax;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 30.07.13
 * Time: 12:14
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class StaXDemo_convertXMLToCSV {
    private static String xml = "<root>" +
            "<Item>\n" +
            "    <ItemID>4504216604</ItemID>\n" +
            "    <ListingDetails>\n" +
            "        <StartTime>10:30:10.000Z</StartTime>\n" +
            "        <!-- Start difference from case 1 -->\n" +
            "         <averages>\n" +
            "            <AverageTime>value1</AverageTime>\n" +
            "            <AveragePrice>value2</AveragePrice>\n" +
            "          </averages>\n" +
            "         <!-- End difference from case 1 -->\n" +
            "         <EndTime>11:00:10.000Z</EndTime>\n" +
            "          <ViewItemURL>http://url</ViewItemURL>\n" +
            "      </ListingDetails>" +
            "</Item>\n" +
            "                </root>";
 /*   "              <category type=\"TX\">9823</category>\n" +
            "              <category type=\"TY\">9112</category>\n" +*/

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        // new StaXDemo_convertXMLToCSV().convertXMLToCSV(new BufferedInputStream(new FileInputStream(args[0])), new BufferedOutputStream(new FileOutputStream(args[1])));
        StringWriter writer = new StringWriter();
        new StaXDemo_convertXMLToCSV().convertXMLToCSV(XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(xml)), writer);
        System.out.println("writer = " + writer);
    }

    static public final String ROOT = "root";
    static public final String ITEM = "Item";
    static public final String ITEM_ID = "ItemID";
    static public final String ITEM_DETAILS = "ListingDetails";
    static public final String START_TIME = "StartTime";
    static public final String END_TIME = "EndTime";
    static public final String ITEM_URL = "ViewItemURL";
    static public final String AVERAGES = "averages";
    static public final String AVERAGE_TIME = "AverageTime";
    static public final String AVERAGE_PRICE = "AveragePrice";
    static public final String SEPARATOR = ",";

    public void convertXMLToCSV(InputStream in, OutputStream out) throws XMLStreamException {
        PrintWriter writer = new PrintWriter(out);
        XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(in);
        convertXMLToCSV(xmlStreamReader, writer);
    }

    public void convertXMLToCSV(XMLStreamReader xmlStreamReader, Writer writer) throws XMLStreamException {
        try {
            writer.write("ItemID,StartTime,EndTime,ViewItemURL,AverageTime,AveragePrice\n");

            xmlStreamReader.nextTag();
            xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, ROOT);

            while (xmlStreamReader.hasNext()) {
                xmlStreamReader.nextTag();
                if (xmlStreamReader.isEndElement())
                    break;

                xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, ITEM);
                String itemID = nextValue(xmlStreamReader, ITEM_ID);
                xmlStreamReader.nextTag();
                xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, ITEM_DETAILS);
                String startTime = nextValue(xmlStreamReader, START_TIME);
                xmlStreamReader.nextTag();
                String averageTime = null;
                String averagePrice = null;

                if (xmlStreamReader.getLocalName().equals(AVERAGES)) {
                    averageTime = nextValue(xmlStreamReader, AVERAGE_TIME);
                    averagePrice = nextValue(xmlStreamReader, AVERAGE_PRICE);
                    xmlStreamReader.nextTag();
                    xmlStreamReader.require(XMLStreamConstants.END_ELEMENT, null, AVERAGES);
                    xmlStreamReader.nextTag();
                }
                String endTime = currentValue(xmlStreamReader, END_TIME);
                String url = nextValue(xmlStreamReader, ITEM_URL);
                xmlStreamReader.nextTag();
                xmlStreamReader.require(XMLStreamConstants.END_ELEMENT, null, ITEM_DETAILS);
                xmlStreamReader.nextTag();
                xmlStreamReader.require(XMLStreamConstants.END_ELEMENT, null, ITEM);

                writer.append(esc(itemID)).append(SEPARATOR)
                        .append(esc(startTime)).append(SEPARATOR)
                        .append(esc(endTime)).append(SEPARATOR)
                        .append(esc(url));
                if (averageTime != null)
                    writer.append(SEPARATOR).append(esc(averageTime)).append(SEPARATOR)
                            .append(esc(averagePrice));
                writer.append("\n");
            }

            xmlStreamReader.require(XMLStreamConstants.END_ELEMENT, null, ROOT);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String esc(String string) {
        if (string.indexOf(',') != -1)
            string = '"' + string + '"';
        return string;
    }

    private String nextValue(XMLStreamReader xmlStreamReader, String name) throws XMLStreamException {
        xmlStreamReader.nextTag();
        return currentValue(xmlStreamReader, name);
    }

    private String currentValue(XMLStreamReader xmlStreamReader, String name) throws XMLStreamException {
        xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, name);
        String value = "";
        for (; ; ) {
            int next = xmlStreamReader.next();
            if (next == XMLStreamConstants.CDATA || next == XMLStreamConstants.SPACE || next == XMLStreamConstants.CHARACTERS)
                value += xmlStreamReader.getText();
            else if (next == XMLStreamConstants.END_ELEMENT)
                break;
            // ignore comments, PIs, attributes
        }
        xmlStreamReader.require(XMLStreamConstants.END_ELEMENT, null, name);
        return value.trim();
    }
}
