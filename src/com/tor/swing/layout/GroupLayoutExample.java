package com.tor.swing.layout;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: tor
 * Date: 04.11.13
 * Time: 11:14
 * http://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html
 */
public class GroupLayoutExample extends JDialog {
    private JLabel label = new JLabel("Find");
    private JTextField textField = new JTextField();
    private JCheckBox caseCheckBox = new JCheckBox("case");
    private JCheckBox wholeCheckBox = new JCheckBox("whole 0123456789");
    private JCheckBox wrapCheckBox = new JCheckBox("wrap");
    private JCheckBox backCheckBox = new JCheckBox("back");
    private JButton findButton = new JButton("Find");
    private JButton cancelButton = new JButton("Cancel");

    public GroupLayoutExample() {
        super();
        init();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

    }

    private void init() {
        caseCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        wrapCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        wholeCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        backCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(label)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(textField)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(caseCheckBox)
                                                .addComponent(wholeCheckBox)
                                        )
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(wrapCheckBox)
                                                .addComponent(backCheckBox)
                                        )
                                ))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(findButton)
                                .addComponent(cancelButton)
                        )

        );
        layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label)
                                .addComponent(textField)
                                .addComponent(findButton)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(caseCheckBox)
                                                .addComponent(wrapCheckBox))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(wholeCheckBox)
                                                .addComponent(backCheckBox))
                                )
                                .addComponent(cancelButton)
                        )

        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                            "javax.swing.plaf.metal.MetalLookAndFeel");
//                            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//                      "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//                    UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                new GroupLayoutExample().setVisible(true);
            }
        });

    }
}
