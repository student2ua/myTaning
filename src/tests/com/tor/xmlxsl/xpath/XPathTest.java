package com.tor.xmlxsl.xpath;
import junit.framework.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
/**
 * User: tor
 * Date: 10.12.13
 * Time: 19:22
 * http://dev64.wordpress.com/2013/02/18/xpath-with-predicates-for-ignoring-namespaces/
 */
public class XPathTest {
    public static final String TEST_SOAP = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "\n" + "<SOAP-ENV:Envelope\n" +
            "  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
            "  xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"\n" +
            "  xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\">\n" +
            "   <SOAP-ENV:Body>\n" +
            "     <SOAP-ENV:Fault>\n" +
            "     <faultcode xsi:type=\"xsd:string\">SOAP-ENV:Client</faultcode>\n" +
            "     <faultstring xsi:type=\"xsd:string\">\n" +
            "   Failed to locate method (ValidateCreditCard) in class\n" +
            "   (examplesCreditCard) at /usr/local/ActivePerl-5.6/lib/\n" +
            "     site_perl/5.6.0/SOAP/Lite.pm line 1555.\n" +
            "        </faultstring>\n" +
            "\n" +
            "      </SOAP-ENV:Fault>\n" +
            "  </SOAP-ENV:Body>\n" +
            "</SOAP-ENV:Envelope>";


    public static Document fromXML(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
    }

    @Test
    public void testGetAttribute() throws Exception {
        Document doc = fromXML(TEST_SOAP);
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expr = xpath.compile("/*[local-name() = 'Envelope']/*[local-name() = 'Body']/" +
                "/*[local-name() = 'Fault']/*[local-name() = 'faultstring']/@*[local-name()='type']");

        String result = expr.evaluate(doc);
        Assert.assertEquals("xsd:string", result);
    }


    @Test
    public void testGetValue() throws Exception {
        Document doc = fromXML(TEST_SOAP);
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expr = xpath.compile("/*[local-name() = 'Envelope']/*[local-name() = 'Body']/" +
                "/*[local-name() = 'Fault']/*[local-name() = 'faultcode']/text()");

        String result = expr.evaluate(doc);
        Assert.assertEquals("SOAP-ENV:Client", result);
    }


}
