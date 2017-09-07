package com.tor.awt.fonts;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Date: 07.09.17
 * Time: 20:16
 * add oue font
 */
public class AddFontDemo {
    public static void main(String[] args) {
        try {
//            URL fontUrl = new URL("https://fonts.google.com/specimen/PT+Sans");
//            URL fontUrl = new URL("https://fonts.googleapis.com/css?family=Open+Sans");
//            URL fontUrl = new URL("https://cdn.rawgit.com/tonsky/FiraCode/master/distr/ttf/FiraCode-Regular.ttf");
            URL fontUrl = new URL("https://github.com/todylu/monaco.ttf/raw/master/monaco.ttf");
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            final Font font = Font.createFont(Font.TRUETYPE_FONT,fontUrl.openStream());
            ge.registerFont(font);


            JLabel l = new JLabel(
                    "В чащах юга жил-был цитрус... — да, но фальшивый экземпляръ!?і₴їє");
//            Эй, жлоб! Где туз? Прячь юных съёмщиц в шкаф.!?і₴їє
            l.setFont(font);
            JOptionPane.showMessageDialog(null, l);
        } catch (IOException e) {
           e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }


    }
}
