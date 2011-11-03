package com.tor.xmlxsl.flatFileToXML;
 /** пример правильной транчформации файла xml */
import java.io.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

public class ToXML  {

  BufferedReader in;
  StreamResult out;

  Document xmldoc;
  Element root;

  public static void main (String args[]) {
      new ToXML().doit();
  }

  public void doit () {
    try{
      in = new BufferedReader(new FileReader("data.txt"));
      out = new StreamResult("data.xml");
      initXML();
      String str;
      while ((str = in.readLine()) != null) {
         process(str);
      }
      in.close();
      writeXML();
    }
    catch (Exception e) { e.printStackTrace(); }
  }


  public void initXML() throws ParserConfigurationException{
    // JAXP + DOM
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    DOMImplementation impl = builder.getDOMImplementation();

    xmldoc = impl.createDocument(null, "HOWTOS", null);
    root = xmldoc.getDocumentElement();
  }

  public void process(String s) {
    // Since the separator character "|" has special meaning
    // with regular expression, we need to escape it.
    String [] elements = s.split("\\|");
    Element e0 = xmldoc.createElement("TOPIC");

    Element e1 = xmldoc.createElement("TITLE");
    Node  n1 = xmldoc.createTextNode(elements[0]);
    e1.appendChild(n1);

    Element e2 = xmldoc.createElement("URL");
    Node  n2 = xmldoc.createTextNode(elements[1]);
    e2.appendChild(n2);

    e0.appendChild(e1);
    e0.appendChild(e2);
    root.appendChild(e0);
  }

  public void writeXML() throws TransformerConfigurationException,
          TransformerException {
    DOMSource domSource = new DOMSource(xmldoc);
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
   // transformer.setOutputProperty (OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
    // we want to pretty format the XML output
    // note : this is broken in jdk1.5 beta!
    transformer.setOutputProperty
       ("{http://xml.apache.org/xslt}indent-amount", "4");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    //
    transformer.transform(domSource, out);
    /*
      get the XML in a String
          java.io.StringWriter sw = new java.io.StringWriter();
          StreamResult sr = new StreamResult(sw);
          transformer.transform(domSource, sr);
          return sw.toString();
    */
  }
}