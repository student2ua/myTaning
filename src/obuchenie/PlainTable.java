package obuchenie;

import com.tor.swing.table.cellRenderer.IndicatorCellRenderer;
import com.tor.swing.table.cellRenderer.MultyLineCellRenderer;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.08.2008
 * Time: 12:37:12
 * To change this template use File | Settings | File Templates.
 */
public class PlainTable extends JFrame {
    private static final Logger log = Logger.getLogger(PlainTable.class);
    private static final int MIN = 0;
    private static final int MAX = 100;

    public JTable getJTable() {
        return jTable;
    }

    public void setJTable(JTable jTable) {
        this.jTable = jTable;
    }

    private  JTable jTable =new JTable();
    public PlainTable() throws HeadlessException {
        super("Просто таблица");setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jTable.setModel(new PineTableModel());
        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new JScrollPane(jTable), BorderLayout.CENTER);
        JButton b =new JButton("next TableModel");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                jTable.setModel(new MyMultiLineTableModel());
                indicatorSet(1);
                multyLineSet(0);
                jTable.setRowHeight(jTable.getRowHeight()*2);
                jTable.validate();
            }
        });
        c.add(b, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    private void multyLineSet(int col) {
      MultyLineCellRenderer renderer=new MultyLineCellRenderer();
      renderer. setBackground(jTable.getBackground());
       // MultiLineCellRenderer renderer=new MultiLineCellRenderer(SwingConstants.LEFT, SwingConstants.CENTER);
        jTable.getColumnModel().getColumn(col).setCellRenderer(renderer);
    }

    public static void main(String[] args) {
     PlainTable p=   new PlainTable();
    }
    private void indicatorSet(int col){
        IndicatorCellRenderer renderer = new IndicatorCellRenderer(MIN, MAX);
        renderer.setStringPainted(true);
        renderer.setBackground(jTable.getBackground());
// set limit value and fill color
        Hashtable limitColors = new Hashtable();
        limitColors.put(new Integer(16), Color.green);
        limitColors.put(new Integer(20), Color.yellow);
        limitColors.put(new Integer(25), Color.red);
        renderer.setLimits(limitColors);
        jTable.getColumnModel().getColumn(col).setCellRenderer(renderer);
    }
}
