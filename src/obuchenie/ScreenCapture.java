package obuchenie;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.03.12
 * Time: 13:21
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * Снятие скриншота рабочего стола
 */
public class ScreenCapture {
    public static void main(String[] args) {
        /**     Hi all,
         * Let us see how to take screenshot and save it as a JPG file with the use of Java Program. It can be acheived
         * by the use of ImageIO API for saving the screenshot as the JPG file and to take screenshot with the Robot class.
         * So as to proceed further, first the screen dimension should be find and we can create a rectangle with this
         * dimension. The size of the screen can be found using the Toolkit class. The following code snippet will help you
         * to find the screen size.
         */
//to find the screen dimension
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            Rectangle rectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);
/**  After getting the screen dimension create an instance of the Robot class and copy the rectangle from the screen.
 *  The createScreenCapture() method in the Robot class can be used to get the BufferedImage of the rectangular
 *  dimension passed as argument to it. The following code snippet will help you to do the above said things.
 */

//to get the BufferedImage with createScreenCapture method

            Robot robot = new Robot();

            BufferedImage image = robot.createScreenCapture(rectangle);

            /**  So further creating the JPG file from the BufferedImage is possible through the ImageIO API to convert
             *  the BufferedImage to the JPG file. The following code snippet will help you in converting the BufferedImage to a JPG file
             */


//convert BufferedImage to JPG File

            File file = new File("screen.jpg");
            ImageIO.write(image, "jpg", file);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
