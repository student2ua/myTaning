package com.tor.awt.graphics2D.print;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: tor
 * Date: 010, 10.08.2013
 * Time: 19:48
 * http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/0540__Print.htm
 */
public class PrintDemo implements Printable {
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        /* We have only one page, and 'page' is zero-based */
        if (pageIndex > 0) return NO_SUCH_PAGE;
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        graphics.drawString("Test string", 100, 100);
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public PrintRequestAttributeSet sampleSetup() {
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        // Request landscape mode
        pras.add(OrientationRequested.LANDSCAPE);
        // European A4 paper
        pras.add(MediaSizeName.ISO_A4);
        // Request stapling     сшивание?
        pras.add(Finishings.STAPLE);
        // Collate multiple copies       сопоставление нескольких копий?
        pras.add(SheetCollate.COLLATED);
        // Request 2-sided
        pras.add(Sides.DUPLEX);
        // 2 pages to a sheet
        pras.add(new NumberUp(2));
        // how many copies
        pras.add(new Copies(2));
        return pras;
    }

    public static void printToFile(String outFile, String outFileFormat, PrintRequestAttributeSet pras, String inputFile) throws IOException {
        StreamPrintServiceFactory[] factories = StreamPrintServiceFactory.lookupStreamPrintServiceFactories(null, outFileFormat);
        // Error message if we can't print to the specified output type
        if (factories.length == 0) {
            System.out.println("Unable to print files of type: " + outFileFormat);
            return;
        }
        FileOutputStream out = new FileOutputStream(outFile);
        StreamPrintService service = factories[0].getPrintService(out);
        printToService(service,inputFile,pras);
        out.close();
        
    }
    // Print the contents of the named file to the specified PrintService,
    // requesting the specified attributes.
    // This is shared code used by print() and printToFile() above.
    public static void printToService(PrintService service, String filename,
                                      PrintRequestAttributeSet attributes) throws IOException {
        // Figure out what type of file we're printing
        DocFlavor flavor = getFlavorFromFilename(filename);
        // Open the file
        InputStream in = new FileInputStream(filename);
        // Create a Doc object to print from the file and flavor.
        Doc doc = new SimpleDoc(in, flavor, null);
        // Create a print job from the service
        DocPrintJob job = service.createPrintJob();

        // Monitor the print job with a listener
        job.addPrintJobListener(new PrintJobAdapter() {
            public void printJobCompleted(PrintJobEvent e) {
                System.out.println("Print job complete");
                System.exit(0);
            }

            public void printDataTransferCompleted(PrintJobEvent e) {
                System.out.println("Document transfered to printer");
            }

            public void printJobRequiresAttention(PrintJobEvent e) {
                System.out.println("Print job requires attention");
                System.out.println("Check printer: out of paper?");
            }

            public void printJobFailed(PrintJobEvent e) {
                System.out.println("Print job failed");
                System.exit(1);
            }
        });

        // Now print the document, catching errors
        try {
            job.print(doc, attributes);
        } catch (PrintException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
    // A utility method to return a DocFlavor object matching the
    // extension of the filename.
    public static DocFlavor getFlavorFromFilename(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        extension = extension.toLowerCase();
        if (extension.equals("gif"))
            return DocFlavor.INPUT_STREAM.GIF;
        else if (extension.equals("jpeg"))
            return DocFlavor.INPUT_STREAM.JPEG;
        else if (extension.equals("jpg"))
            return DocFlavor.INPUT_STREAM.JPEG;
        else if (extension.equals("png"))
            return DocFlavor.INPUT_STREAM.PNG;
        else if (extension.equals("ps"))
            return DocFlavor.INPUT_STREAM.POSTSCRIPT;
        else if (extension.equals("txt"))
            return DocFlavor.INPUT_STREAM.TEXT_PLAIN_HOST;
            // Fallback: try to determine flavor from file content
        else
            return DocFlavor.INPUT_STREAM.AUTOSENSE;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PageFormat pf = job.pageDialog(aset);
        job.setPrintable(new PrintDemo(), pf);
        final boolean ok = job.printDialog(aset);
        if (ok) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);


    }
}
