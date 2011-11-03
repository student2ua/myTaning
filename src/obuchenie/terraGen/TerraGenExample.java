package obuchenie.terraGen;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 30.06.2010
 * Time: 14:40:20
 * To change this template use File | Settings | File Templates.
 */
public class TerraGenExample {
    private static final Logger log = Logger.getLogger(TerraGenExample.class);


    /**
     * @param args the command line arguments
     */
    static int[][] matr;
    static final int SIZE_FOR_HOR = 100;
    static final int SIZE_FOR_VERT = 100;
    static final int COUNT_OF_NO_GRND = 56;
    static int hor_coord = 0;
    static int vert_coord = 0;

    public static void main(String[] args) {
        try {
            Random rnd = new Random();
            matr = new int[SIZE_FOR_HOR][SIZE_FOR_VERT];
            //create plane
            for (int i = 0; i < matr.length; i++) {
                for (int j = 0; j < matr[i].length; j++) {
                    matr[i][j] = 0;
                }
            }
            for (int i = 0; i < COUNT_OF_NO_GRND; i++) {
                //create water
                matr[rnd.nextInt(SIZE_FOR_HOR)][rnd.nextInt(SIZE_FOR_VERT)] = 1;
            }
            File img_out = new File("./img.png");
            BufferedImage buff = new BufferedImage(SIZE_FOR_HOR * 10, SIZE_FOR_VERT * 10, 2);
            Graphics2D g = buff.createGraphics();

            for (int i = 0; i < matr.length; i++) {
                for (int j = 0; j < matr[i].length; j++) {
                    if (matr[i][j] == 0) {
                        g.setPaint(Color.WHITE);
                    } else {
                        if (matr[i][j] == 1) {
                            g.setPaint(Color.CYAN);
                        }
                    }

                    g.fillRect(hor_coord, vert_coord, hor_coord + 10, vert_coord + 10);
                    hor_coord = hor_coord + 10;
                }
                vert_coord = vert_coord + 10;
                hor_coord = 0;
            }
            ImageIO.write(buff, "PNG", img_out);
        } catch (IOException ex) {
            //  Logger.getLogger(TerraGenExample.class.getName()).log(Level.SEVERE, null, ex);
            log.error(ex);
        }

    }
}
