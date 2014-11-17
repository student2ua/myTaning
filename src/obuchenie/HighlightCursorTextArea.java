package obuchenie;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: tor
 * Date: 02.06.14
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class HighlightCursorTextArea extends JTextArea {
    private static final Color LINE_COLOR = new Color(250, 250, 220);
    private final DefaultCaret caret;

    public HighlightCursorTextArea() {
        super();
        setOpaque(false);
        caret = new DefaultCaret() {
            @Override
            protected synchronized void damage(Rectangle r) {
                if (r != null) {
                    JTextComponent c = getComponent();
                    x = 0;
                    y = r.y;
                    width = c.getSize().width;
                    height = r.height;
                    c.repaint();
                }
            }
        };
        caret.setBlinkRate(getCaret().getBlinkRate());
        setCaret(caret);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Insets i = getInsets();
        int h = caret.height;
        int y = caret.y;
        g2.setPaint(LINE_COLOR);
        g2.fillRect(i.left, y, getSize().width - i.left - i.right, h);
        g2.dispose();
        super.paintComponent(g);
    }

    public static void main(String[] args) {
        JPanel mp = new JPanel(new BorderLayout());
        final JCheckBox check = new JCheckBox("LineWrap");
        final HighlightCursorTextArea textArea = new HighlightCursorTextArea();
        textArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        textArea.setText("Highlight Cursor Test\n\naaaaaaaaaaaasdfasdfasdfasdfsadffasdfas");

        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setLineWrap(check.isSelected());
                textArea.requestFocusInWindow();
            }
        });
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.getViewport().setBackground(Color.WHITE);
        mp.add(check, BorderLayout.NORTH);
        mp.add(scroll);
        mp.setPreferredSize(new Dimension(320, 240));
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("@title@");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(mp);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
