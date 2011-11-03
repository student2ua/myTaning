package com.tor.swing.jtabbetpane.frame;

import com.tor.swing.jtabbetpane.CloseTabbedPane;
import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 01.11.2010
 * Time: 17:17:41
 * To change this template use File | Settings | File Templates.
 */
public class TabFrameDemo extends JFrame {
    private CloseTabbedPane tabbedPane;
  	private JMenuBar menuBar;
   	private JMenu menu;
   	private JMenuItem menuItem;
   	public TabFrameDemo(){
   		menuBar = new JMenuBar();
   		menu = new JMenu("File");
   		menuItem = new JMenuItem("Add Tab");
   		MenuHandler handler = new MenuHandler(this);
   		menuItem.addActionListener(handler);
   		menu.add(menuItem);
   		menuBar.add(menu);

   		setJMenuBar(menuBar);
   		tabbedPane = new CloseTabbedPane();
   		getContentPane().add(tabbedPane);

   		setSize(500, 400);
   		setVisible(true);
   		setLocationRelativeTo(null);
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	}
   	public CloseTabbedPane getTabbedPane(){
   		return tabbedPane;
   	}

   	public static void main(String[] args) {
   		TabFrameDemo frame = new TabFrameDemo();
   	}
}
