package com.tor.pattens.mvc;

import com.tor.pattens.mvc.model.DataModel;
import com.tor.pattens.mvc.view.JComboView;
import com.tor.pattens.mvc.view.ListView;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2009
 * Time: 14:47:48
 * To change this template use File | Settings | File Templates.
 */
public class StartUp extends JFrame {
    private static final Logger log = Logger.getLogger(StartUp.class);
    private ListView listView = new ListView();
    private JComboView comboView = new JComboView();
    private DataModel model = new DataModel();
    private JButton m_btnAdd = new JButton();
    private JButton m_btnExit = new JButton();
    private JTextField m_txtField = new JTextField(20);

    public StartUp() throws HeadlessException {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension(405, 325);
        this.setSize(frameSize);
        this.setTitle("MVC Example");
        this.setLocation(
                (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2
        );
        this.getContentPane().setLayout(null);
        JPanel a_pnl1 = new JPanel();
        JPanel a_pnl2 = new JPanel();

        JPanel a_pnl3 = new JPanel();
        JPanel a_pnl4 = new JPanel();

        JLabel lblComboBox = new JLabel();

        a_pnl1.setBounds(new Rectangle(3, 5, 393, 290));

        a_pnl1.setLayout(new BoxLayout(a_pnl1, BoxLayout.Y_AXIS));

        a_pnl2.setBorder(BorderFactory.createEtchedBorder());

        a_pnl2.setLayout(new BorderLayout());

        a_pnl3.setBorder(BorderFactory.createEtchedBorder());

        a_pnl4.setBorder(BorderFactory.createEtchedBorder());

        m_btnAdd.setText("Add");
        m_btnExit.setText("Exit");

        lblComboBox.setText("Items:");

        m_txtField.setMaximumSize(new Dimension(4, 21));

        this.getContentPane().add(a_pnl1, null);

        a_pnl1.add(a_pnl2, null);
        a_pnl2.add(listView, BorderLayout.CENTER);
        a_pnl1.add(a_pnl3, null);
        a_pnl3.add(lblComboBox, null);
        a_pnl3.add(comboView, null);
        a_pnl1.add(a_pnl4, null);
        a_pnl4.add(m_txtField, null);
        a_pnl4.add(m_btnAdd, null);
        a_pnl4.add(m_btnExit, null);
        m_btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        AdapterEditFildToModel editFildToModel = new AdapterEditFildToModel(m_btnAdd, m_txtField, model);
        AdapterModelToListView modelToListView = new AdapterModelToListView(listView, model);
        AdapterModelToComboView modelToComboView = new AdapterModelToComboView(comboView, model);
        m_btnAdd.addActionListener(editFildToModel);
        model.addChangeListener(modelToListView);
        model.addChangeListener(modelToComboView);
        this.setVisible(true);
    }

    private static class AdapterEditFildToModel implements ActionListener {
        private DataModel adapterDataModel;
        private JButton m_btnAdd;
        private JTextField m_txtField;

        public AdapterEditFildToModel(JButton p_btn, JTextField p_txt, DataModel p_mod) {
            adapterDataModel = p_mod;
            m_btnAdd = p_btn;
            m_txtField = p_txt;
        }

        public void actionPerformed(ActionEvent e) {
            
            adapterDataModel.addData(m_txtField.getText().trim());
        }
    }

    private class AdapterModelToListView implements ChangeListener {
        private DataModel adapterDataModel;
        private ListView listView;

        private AdapterModelToListView(ListView m_lst, DataModel adapterDataModel) {
            this.adapterDataModel = adapterDataModel;
            this.listView = m_lst;
        }

        public void stateChanged(ChangeEvent e) {
            this.listView.addData(adapterDataModel.getDate());
        }
    }

    private class AdapterModelToComboView implements ChangeListener {
        private JComboView comboView;
        private DataModel adapterDataModel;

        private AdapterModelToComboView(JComboView comboView, DataModel adapterDataModel) {
            this.comboView = comboView;
            this.adapterDataModel = adapterDataModel;
        }

        public void stateChanged(ChangeEvent e) {
            this.comboView.addData(adapterDataModel.getDate());
        }
    }

    public static void main(String[] args) {
        new StartUp();
    }
}
