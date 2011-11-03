package com.tor.swing;

import com.tor.swing.table.ExcelAdapter;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 03.09.2008
 * Time: 18:29:10
 * Ниже приведён код простого приложения ExcelAdapterExample.java, который, используя ExcelAdapter,
 * делает JTable Excel-совместимым.

 */
public class ExcelAdapterExample  extends JFrame{
    private static final Logger log = Logger.getLogger(ExcelAdapterExample.class);




   BorderLayout borderLayout1 = new BorderLayout();
   JTable jTable1;
   Object[][] data = new Object[4][4];
   Object header[] = { "Jan", "Feb", "Mar", "Apr" };

   public static void main(String args[]) {
      ExcelAdapterExample myframe = new ExcelAdapterExample();
      myframe.setSize(new Dimension(250, 250));

      myframe.setVisible(true);
   }

   public ExcelAdapterExample() {
      super();
      try {

         jbInit();

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception {
      for (int i = 0; i < 4; i++)
         for (int j = 0; j < 4; j++)
            data[i][j] = new Integer(i * 10 + j);
      System.out.println("Header length=" + header[1]);
      jTable1 = new JTable(data, header);
      jTable1.setCellSelectionEnabled(true);
      this.setTitle("Excel Lent JTABLE");
      jTable1.setBackground(Color.pink);

      this.getContentPane().setLayout(borderLayout1);
       //this.addMouseListener();
       this. setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setSize(new Dimension(400, 300));
      this.setBackground(Color.white);
      this.getContentPane().add(jTable1, BorderLayout.CENTER);
      // This is the line that does all the magic!
      ExcelAdapter myAd = new ExcelAdapter(jTable1);

   }
}

