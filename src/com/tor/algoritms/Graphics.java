package com.tor.algoritms;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.10.2010
 * Time: 12:19:11
 * Конвертация цветового пространства CMYK->RGB
 */
public class Graphics {
    private static final Logger log = Logger.getLogger(Graphics.class);

    public static BufferedImage manualColorConvertCMYKtoRGB(BufferedImage bufferedImage) {

        int w = bufferedImage.getWidth(), h = bufferedImage.getHeight();
        //blank canvas
        BufferedImage resultVersion = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = resultVersion.createGraphics();
        //white background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, w, h);
        //paint on image
        g2.drawImage(bufferedImage, 0, 0, null);
        //invert colors
        short[] invert = new short[256];
        for (int i = 0; i < invert.length; i++) {
            invert[i] = (short) (255 - i);
        }
        LookupOp op = new LookupOp(new ShortLookupTable(0, invert), null);
        op.filter(resultVersion, resultVersion);
        return resultVersion;
    }
}
