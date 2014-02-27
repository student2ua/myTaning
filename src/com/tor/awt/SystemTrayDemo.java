package com.tor.awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.08.13
 * Time: 14:18
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * To change this template use File | Settings | File Templates.
 */
public class SystemTrayDemo {
    public static void main(String[] args) {
        final TrayIcon trayIcon;

        if (!SystemTray.isSupported()) {
            System.err.println("System tray is not supported.");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        PropertyChangeListener pcl;
        pcl = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent pce) {
                System.out.println("Property changed = " + pce.getPropertyName());
                TrayIcon[] tia = (TrayIcon[]) pce.getOldValue();
                if (tia != null) {
                    System.out.println("Old tray icon array contents: ");
                    for (int i = 0; i < tia.length; i++)
                        System.out.println(tia[i]);
                    System.out.println();
                }

                tia = (TrayIcon[]) pce.getNewValue();
                if (tia != null) {
                    System.out.println("New tray icon array contents: ");
                    for (int i = 0; i < tia.length; i++)
                        System.out.println(tia[i]);
                    System.out.println();
                }
            }
        };
        tray.addPropertyChangeListener("trayIcons", pcl);


        PopupMenu popup = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        popup.add(exitItem);

        Image image = Toolkit.getDefaultToolkit().getImage("cookie.png");
        trayIcon = new TrayIcon(image, "Your Fortune", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trayIcon.displayMessage("How do I turn this off?",
                        "Right-click on the fortune cookie and select Exit.", TrayIcon.MessageType.INFO);
            }
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
            return;
        }

        final List<String> fortunes = readFortunes();
        Timer timer = new Timer(10000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = (int) (fortunes.size() * Math.random());
                trayIcon.displayMessage("Your Fortune", fortunes.get(index), TrayIcon.MessageType.INFO);
            }
        });
        timer.start();
    }

    private static List<String> readFortunes() {
        List<String> fortunes = new ArrayList<String>();
  /*     try {
         Scanner in = new Scanner(new File("/com/tor/xmlxsl/flatFileToXML/data.txt"));
         StringBuilder fortune = new StringBuilder();
         while (in.hasNextLine()) {
           String line = in.nextLine();
           if (line.equals("%")) {
             fortunes.add(fortune.toString());
             fortune = new StringBuilder();
           } else {
             fortune.append(line);
             fortune.append(' ');
           }
         }
       } catch (IOException ex) {
         ex.printStackTrace();
       }*/
        return fortunes;
    }
}
