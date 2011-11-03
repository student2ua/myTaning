package com.tor.swing;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 14.01.2011
 * Time: 16:55:01
 * To change this template use File | Settings | File Templates.
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestFontMetrics extends JFrame {

	public static void createGUI() {
		JFrame frame = new JFrame("Test frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(150, 200));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		label.setFont(new Font("Verdana", Font.PLAIN, 20));
		panel.add(label);

		final JTextField textField = new JTextField();
		textField.setAlignmentX(JTextField.CENTER_ALIGNMENT);

		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String fullText = textField.getText();
				String newText = "";

				for (int i = 0; i <= fullText.length(); i++) {
					String text = fullText.substring(0, i);
					Graphics g = label.getGraphics();
					FontMetrics fm = g.getFontMetrics();
					int width = fm.stringWidth(text);

					if (width > 200) {
						newText += "...";
						break;
					}

					newText = text;
				}

				label.setText(newText);
			}
		});

		panel.add(textField);

		frame.getContentPane().add(panel);
		//frame.setPreferredSize()(new Dimension(250, 200));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				createGUI();
			}
		});
	}
}
