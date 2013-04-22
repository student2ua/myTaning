package com.tor.util;

import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.03.13
 * Time: 17:44
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class PropertiesAndXML_Example {
    private String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n" +
            "<properties>\n" +
            "<comment>email</comment>\n" +
            "<entry key=\"email\">example@javacodegeeks.com</entry>\n" +
            "</properties>";

    @Test
    public void XMLtoProperties() throws IOException {
        Properties properties = new Properties();
        properties.loadFromXML(new ByteArrayInputStream(xmlStr.getBytes()));
        Assert.assertEquals(properties.get("email"), "example@javacodegeeks.com");
    }

    @Test
    public void PropertiesToXML() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Properties properties = new Properties();
        properties.setProperty("email", "example@javacodegeeks.com");
        properties.storeToXML(outputStream, "email", "UTF-8");
        String s = outputStream.toString("UTF-8");
        Assert.assertEquals(s, xmlStr); // различие в standalone="no"
    }
    //todo @see public void load(Reader reader) throws IOException
}
