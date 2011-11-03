package com.tor.swing;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 31.03.2010
 * Time: 16:33:38
 * To change this template use File | Settings | File Templates.
 */
public class JInputLocaleIndicator extends JLabel {
    private static final Logger log = Logger.getLogger(JInputLocaleIndicator.class);

    private static class MonitoringThread extends Thread {

        private static final long REFRESH_TIMEOUT = 1000L;

        public MonitoringThread() {
            setPriority(NORM_PRIORITY);
            setDaemon(true);
        }

        public void run() {
            while (!interrupted()) {
                synchronized (indicatorLists) {
                    for (Iterator it = indicatorLists.entrySet().iterator(); it.hasNext();) {
                        Map.Entry entry = (Map.Entry) it.next();
                        Locale locale = ((Window) entry.getKey()).getInputContext().getLocale();
                        for (Iterator it1 = ((List) entry.getValue()).iterator(); it1.hasNext();) {
                            JLabel label = (JLabel) it1.next();
                            label.setText(locale.getLanguage());
                            label.setToolTipText(locale.getDisplayLanguage());
                        }
                    }
                }

                try {
                    Thread.sleep(REFRESH_TIMEOUT);
                }
                catch (InterruptedException thrown) {
                    break;
                }
            }
        }

    }

    private static Map indicatorLists = new WeakHashMap();      //<Window, List<JLabel>>

    static {
        MonitoringThread monitoringThread = new MonitoringThread();
        monitoringThread.start();
    }

    public JInputLocaleIndicator() {
        addHierarchyListener(new HierarchyListener() {
            public void hierarchyChanged(HierarchyEvent event) {
                if ((event.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) != 0) {
                    Window window = SwingUtilities.getWindowAncestor(JInputLocaleIndicator.this);

                    synchronized (indicatorLists) {
                        java.util.List indicators = (java.util.List) indicatorLists.get(window);

                        if (isDisplayable()) {
                            if (indicators == null) {
                                indicatorLists.put(window, indicators = new ArrayList());
                            }

                            indicators.add(JInputLocaleIndicator.this);
                        } else {
                            if (indicators != null) {
                                indicators.remove(JInputLocaleIndicator.this);
                            }
                        }
                    }
                }
            }
        }
        );
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JInputLocaleIndicator indicator = new JInputLocaleIndicator();

        frame.getContentPane().setLayout(new GridBagLayout());

        frame.getContentPane().add(indicator);
        frame.setSize(512, 384);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
