package obuchenie;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: tor
 * Date: 009, 09.07.2023
 * Time: 20:32
 * https://www.javatips.net/api/openpnp-master/src/main/java/org/openpnp/gui/support/SvgIcon.java
 */

public class SvgIcon implements Icon {
    /**
     * The BufferedImage generated from the SVG document.
     */
    protected BufferedImage bufferedImage;

    protected Image bufferedImageDisabled;

    /**
     * The width of the rendered image.
     */
    protected int width;

    /**
     * The height of the rendered image.
     */
    protected int height;

    /**
     * Create a new SVGIcon object.
     *
     * @param uri The URI to read the SVG document from.
     */
    public SvgIcon(URL url, int width, int height) {
        try {
            generateBufferedImage(new TranscoderInput(url.toString()), width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new SVGIcon object.
     *
     * @param uri The URI to read the SVG document from.
     */
    public SvgIcon(URL url) {
        this(url, 24, 24);
    }

    /**
     * Generate the BufferedImage.
     */
    protected void generateBufferedImage(TranscoderInput in, int w, int h)
            throws TranscoderException {
        BufferedImageTranscoder t = new BufferedImageTranscoder();
        if (w != 0 && h != 0) {
            t.setDimensions(w, h);
        }
        t.transcode(in, null);
        bufferedImage = t.getBufferedImage();
        bufferedImageDisabled = GrayFilter.createDisabledImage(bufferedImage);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    // Icon //////////////////////////////////////////////////////////////////

    /**
     * Returns the icon's width.
     */
    public int getIconWidth() {
        return width;
    }

    /**
     * Returns the icon's height.
     */
    public int getIconHeight() {
        return height;
    }

    /**
     * Draw the icon at the specified location.
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Image image = bufferedImage;
        if (c != null && !c.isEnabled()) {
            image = bufferedImageDisabled;
        }
        g.drawImage(image, x, y, null);
    }

    /**
     * A transcoder that generates a BufferedImage.
     */
    protected class BufferedImageTranscoder extends ImageTranscoder {

        /**
         * The BufferedImage generated from the SVG document.
         */
        protected BufferedImage bufferedImage;

        /**
         * Creates a new ARGB image with the specified dimension.
         *
         * @param width  the image width in pixels
         * @param height the image height in pixels
         */
        public BufferedImage createImage(int width, int height) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        /**
         * Writes the specified image to the specified output.
         *
         * @param img                 the image to write
         * @param output              the output where to store the image
         * @param TranscoderException if an error occured while storing the image
         */
        public void writeImage(BufferedImage img, TranscoderOutput output)
                throws TranscoderException {
            bufferedImage = img;
        }

        /**
         * Returns the BufferedImage generated from the SVG document.
         */
        public BufferedImage getBufferedImage() {
            return bufferedImage;
        }

        /**
         * Set the dimensions to be used for the image.
         */
        public void setDimensions(int w, int h) {
            hints.put(KEY_WIDTH, new Float(w));
            hints.put(KEY_HEIGHT, new Float(h));
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame panel = new JFrame();
                panel.setLayout(new BorderLayout());

                final JButton test_svg = new JButton("test SVG");
                File svgFile=new File("E:\\project\\MyProject\\treeTable\\file-sign.svg");
                try {
                    test_svg.setIcon(new SvgIcon(svgFile.toURI().toURL()));
                } catch (MalformedURLException e) {
                    test_svg.setBackground(Color.RED);
                }
                panel.add(test_svg, BorderLayout.CENTER);

                panel.setTitle("SVG test");
                panel.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                panel.setBounds(400, 400, 300, 300);
                panel.setVisible(true);
            }
        });

    }
}
