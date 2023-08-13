package com.tor.awt.graphics2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * User: tor
 * Date: 013, 13.08.2023
 * Time: 16:47
 */
public class Util2D {
    /**
     * <pre>{@code } //Drawing new image over old image
     * bim = makeWhiteTransparent(bim);
     * bim2 = makeWhiteTransparent(bim2);
     *
     * Graphics2D graphics = (Graphics2D) bim.getGraphics();
     * graphics.drawImage(bim2,0,0, null);
     *
     * g2.drawImage(bim, w/2-wc/2, h/2-hc/2, null);
     * </pre>{code}
     */
    public BufferedImage makeWhiteTransparent(BufferedImage img) {
        BufferedImage dst = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        dst.getGraphics().drawImage(img, 0, 0, null);
        int markerRGB = 0x00ffffff; // Color.WHITE.getRGB() | 0xFF000000;
        int width = dst.getWidth();
        int height = dst.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = dst.getRGB(x, y) & 0x00ffffff;
                if (rgb == markerRGB) {
                    int value = 0x00FFFFFF & rgb;
                    dst.setRGB(x, y, value);
                }
            }
        }
        return dst;
    }

    /*
     *  Create a BufferedImage for Swing components.
     *  All or part of the component can be captured to an image.
     *
     *  @param  component Swing component to create image from
     *  @param  region The region of the component to be captured to an image
     *  @return	image the image for the given region
     */
    public static BufferedImage createImage(JComponent component, Rectangle region) {
        //  Make sure the component has a size and has been layed out.
        //  (necessary check for components not added to a realized frame)

        if (!component.isDisplayable()) {
            Dimension d = component.getSize();

            if (d.width == 0 || d.height == 0) {
                d = component.getPreferredSize();
                component.setSize(d);
            }

            layoutComponent(component);
        }

        BufferedImage image = new BufferedImage(region.width, region.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        //  Paint a background for non-opaque components,
        //  otherwise the background will be black

        if (!component.isOpaque()) {
            g2d.setColor(component.getBackground());
            g2d.fillRect(region.x, region.y, region.width, region.height);
        }

        g2d.translate(-region.x, -region.y);
        component.print(g2d);
        g2d.dispose();
        return image;
    }

    /**
     * Convenience method to create a BufferedImage of the desktop
     *
     * @param fileName name of file to be created or null
     * @throws AWTException see Robot class constructors
     * @throws IOException  if an error occurs during writing
     * @return image the image for the given region
     */
    public static BufferedImage createDesktopImage()
            throws AWTException, IOException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle region = new Rectangle(0, 0, d.width, d.height);
        return createImage(region);
    }

    /**
     * Create a BufferedImage from a rectangular region on the screen.
     *
     * @throws AWTException see Robot class constructors
     * @param     region region on the screen to create image from
     * @return image the image for the given region
     */
    public static BufferedImage createImage(Rectangle region)
            throws AWTException {
        return new Robot().createScreenCapture(region);
    }

    static void layoutComponent(Component component) {
        synchronized (component.getTreeLock()) {
            component.doLayout();

            if (component instanceof Container) {
                for (Component child : ((Container) component).getComponents()) {
                    layoutComponent(child);
                }
            }
        }
    }
}
