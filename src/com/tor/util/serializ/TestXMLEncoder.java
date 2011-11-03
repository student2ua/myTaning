package com.tor.util.serializ;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

public class TestXMLEncoder {
    public static void main(String[] args) throws Exception {
        TestXMLEncoder t = new TestXMLEncoder();
        t.testSimpleBean();
        t.testCompositeBean();
        t.testNoDefaultConstructor();
    }

    private void testSimpleBean() throws Exception {
        System.out.println("Testing simple bean");
        File simpleBeanXml = new File("simplebean.xml");
        SimpleBean b = new SimpleBean();
        b.setName("Java");
        encodeObject(b, simpleBeanXml);
        SimpleBean b2 = (SimpleBean) decodeObject(simpleBeanXml);

        System.out.println("Retrieved: " + b2);
        System.out.println();
    }

    private void testCompositeBean() throws Exception {
        System.out.println("Testing composite bean");
        File compositeBeanXml = new File("compositebean.xml");
        CompositeBean b = new CompositeBean();
        SimpleBean s = new SimpleBean();
        s.setName("Nested");
        b.setS(s);
        encodeObject(b, compositeBeanXml);
        CompositeBean b2 = (CompositeBean) decodeObject(compositeBeanXml);

        System.out.println("Retrieved: " + b2);
        System.out.println();
    }

    private void testNoDefaultConstructor() throws Exception {
        System.out.println("Testing noDefaultConstructor");
        File noDefaultXml = new File("nodefault.xml");
        NotABean b = new NotABean("NotBean");
        encodeObject(b, noDefaultXml);
        NotABean b2 = (NotABean) decodeObject(noDefaultXml);

        System.out.println("Retrieved: " + b2);
        System.out.println();
    }

    void encodeObject(Object o, File xmlFile) throws IOException {
        XMLEncoder encoder = new XMLEncoder(
                new FileOutputStream(xmlFile)
        );
        encoder.writeObject(o);
        encoder.close();
    }

    Object decodeObject(File xmlFile) throws IOException {
        XMLDecoder decoder = new XMLDecoder(
                new FileInputStream(xmlFile)
        );
        Object o = decoder.readObject();
        decoder.close();
        return o;
    }
}