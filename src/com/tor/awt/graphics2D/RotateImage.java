package com.tor.awt.graphics2D;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * Simple program that loads, rotates and displays an image.
 * Uses the file Duke_Blocks.gif, which should be in
 * the same directory.
 *
 * @author MAG
 * @version 20Feb2009
 */
public class RotateImage extends JPanel {
     // Declare an Image object for us to use.
    Image image;

    // Create a constructor method
    public RotateImage(){
       super();
       // Load an image to play with.
       image = Toolkit.getDefaultToolkit().getImage("Duke_Blocks.gif");
    }

    public void paintComponent(Graphics g){
         Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
         g2d.translate(170, 0); // Translate the center of our coordinates.
         g2d.rotate(1);  // Rotate the image by 1 radian.
         g2d.drawImage(image, 0, 0, 200, 200, this);
    }

    public static void main(String arg[]){
       JFrame frame = new JFrame("RotateImage");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(600,400);

       RotateImage panel = new RotateImage();
       frame.setContentPane(panel);
       frame.setVisible(true);
    }
}
