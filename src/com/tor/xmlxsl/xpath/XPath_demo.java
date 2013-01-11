package com.tor.xmlxsl.xpath;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 11.01.13
 * Time: 12:39
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://rosettacode.org/wiki/XML/XPath#Java
 */
public class XPath_demo {
    public static final String xml="<inventory title=\"OmniCorp Store #45x10^3\">" +
            "  <section name=\"health\">" +
            "    <item upc=\"123456789\" stock=\"12\">" +
            "      <name>Invisibility Cream</name>" +
            "      <price>14.50</price>" +
            "      <description>Makes you invisible</description>" +
            "    </item>" +
            "    <item upc=\"445322344\" stock=\"18\">" +
            "      <name>Levitation Salve</name>" +
            "      <price>23.99</price>" +
            "      <description>Levitate yourself for up to 3 hours per application</description>" +
            "    </item>" +
            "  </section>" +
            "  <section name=\"food\">" +
            "    <item upc=\"485672034\" stock=\"653\">" +
            "      <name>Blork and Freen Instameal</name>" +
            "      <price>4.95</price>" +
            "      <description>A tasty meal in a tablet; just add water</description>" +
            "    </item>" +
            "    <item upc=\"132957764\" stock=\"44\">" +
            "      <name>Grob winglets</name>" +
            "      <price>3.56</price>" +
            "      <description>Tender winglets of Grob. Just add water</description>" +
            "    </item>" +
            "  </section>" +
            "</inventory>";

    public static void main(String[] args) {
        System.out.println(xml);
        System.out.println("---------------------------");
        try {
            Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            XPath xPath= XPathFactory.newInstance().newXPath();
            System.out.println(((Node)xPath.evaluate("/inventory/section/item[1]",doc, XPathConstants.NODE)).getAttributes().getNamedItem("upc"));
            NodeList nodeList= (NodeList) xPath.evaluate("/inventory/section/item/price",doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println("node = " + node.getTextContent());
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XPathExpressionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
