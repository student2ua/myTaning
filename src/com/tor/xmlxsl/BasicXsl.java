package com.tor.xmlxsl;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.04.2010
 * Time: 13:35:56
 * трансформация XML  с помощью XSL
 */


public class BasicXsl {
    // This method applies the xslFilename to inFilename and writes
    // the output to outFilename.
    public static void xsl(String inFilename, String outFilename, String xslFilename) {
        try {
            // Create transformer factory
            TransformerFactory factory = TransformerFactory.newInstance();

            // Use the factory to create a template containing the xsl file
            Templates template = factory.newTemplates(new StreamSource(
                    new FileInputStream(xslFilename)));

            // Use the template to create a transformer
            Transformer xformer = template.newTransformer();

            // Prepare the input and output files
            Source source = new StreamSource(new FileInputStream(inFilename));
            Result result = new StreamResult(new FileOutputStream(outFilename));

            // Apply the xsl file to the source file and write the result to the output file
            xformer.transform(source, result);
        } catch (FileNotFoundException e) {
            System.out.println("файлов нету");
        } catch (TransformerConfigurationException e) {

            System.out.println(" An error occurred in the XSL file " + e);
            SourceLocator locator = e.getLocator();
            int col = locator.getColumnNumber();
            int line = locator.getLineNumber();
            String publicId = locator.getPublicId();
            String systemId = locator.getSystemId();
            System.out.println("LineNumber: " + col + ", ColumnNumber: " + line + ", publicId: " + publicId + ", systemId" + systemId);
        } catch (TransformerException e) {
            // An error occurred while applying the XSL file
            // Get location of error in input file
            SourceLocator locator = e.getLocator();
            int col = locator.getColumnNumber();
            int line = locator.getLineNumber();
            String publicId = locator.getPublicId();
            String systemId = locator.getSystemId();
            System.out.println("LineNumber: " + col + ", ColumnNumber: " + line + ", publicId: " + publicId + ", systemId" + systemId);
        }
    }

    public static void main(String[] args) {
        BasicXsl.xsl(
                "C:\\Temp\\_myhomelib\\Лукьяненко Сергей - Ночь накануне.123409.fb2",
                "C:\\Temp\\_myhomelib\\Лукьяненко Сергей - Ночь накануне.123409.tr",
                "D:\\Incoming\\FB2_2_xhtml.xml");
        //гуд
    }
}
