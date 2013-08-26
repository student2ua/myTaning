package com.tor.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 26.08.13
 * Time: 20:58
 * http://www.java2s.com/Code/JavaAPI/javax.swing/implementsInputVerifier.htm
 */
public class InputVerifierTest {
    public static void main(String args[]) {
        JFrame frame = new JFrame("Verifier Sample");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JTextField textField3 = new JTextField();

        InputVerifier verifier = new InputVerifier() {
            public boolean verify(JComponent comp) {
                boolean returnValue;
                JTextField textField = (JTextField) comp;
                try {
                    Integer.parseInt(textField.getText());
                    returnValue = true;
                } catch (NumberFormatException e) {
                    returnValue = false;
                }
                return returnValue;
            }
        };

        textField1.setInputVerifier(verifier);
        textField3.setInputVerifier(verifier);

        frame.add(textField1, BorderLayout.NORTH);
        frame.add(textField2, BorderLayout.CENTER);
        frame.add(textField3, BorderLayout.SOUTH);
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}
