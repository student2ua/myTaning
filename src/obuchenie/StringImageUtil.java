package obuchenie;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.11.2010
 * Time: 17:18:23
 * Создание надписи в картинке jpg
 */
public class StringImageUtil {

    public void paint(Graphics g) {
        t.setToRotation(Math.toRadians(90), 0, 0);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.RED);
        g2.transform(t);
        g2.drawString("AAAAAAAAAAAA", 200, 200);
    }


    AffineTransform t = new AffineTransform();


    private static Font getFont() {
        return new Font("Arial", Font.BOLD, 14);
    }

    public static void makeStringImage(String s, String fileName) {
        try {
            AffineTransform t = new AffineTransform();
            Font f = getFont();


            BufferedImage imgTmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            FontRenderContext ctx = imgTmp.createGraphics().getFontRenderContext();
            Rectangle2D bounds = f.getStringBounds(s, ctx);

            BufferedImage img = new BufferedImage((int) bounds.getHeight(), (int) bounds.getWidth(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();

            // Rotate image on 90 degrees CCW
            t.setToRotation(Math.toRadians(-90), bounds.getHeight() / 2, bounds.getWidth() / 2);
            g2.transform(t);

            // clear rect
            g2.setColor(Color.YELLOW);
            g2.fillRect(0, 0, img.getWidth(), img.getHeight());

            // write String s
            g2.setColor(Color.BLACK);
            double x = 0;
            double y = -bounds.getY();
            g2.drawString(s, (int) x, (int) y);

            // write image to file
            ImageWriter iw = (ImageWriter) ImageIO.getImageWritersByFormatName("jpg").next();
            iw.setOutput(ImageIO.createImageOutputStream(new File(fileName)));
            iw.write(img);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            String fileName = "c:\\aaa.jpg";

            makeStringImage("SSSSSSSS", fileName);
            Runtime.getRuntime().exec("shellrunner.exe " + fileName);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}