package com.tor.swing;

/**
 * User: tor
 * Date: 28.02.26
 * Time: 18:20
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class MarchingAntsBorder implements Border, ActionListener {

    private final Color color;
    private final int thickness;
    private final float dashLength;
    private float phase = 0f;
    private final Timer timer;
    private JComponent component;

    public MarchingAntsBorder(Color color, int thickness, int dashLength, int speedMs) {
        this.color = color;
        this.thickness = thickness;
        this.dashLength = dashLength;
        this.timer = new Timer(speedMs, this);
        timer.setRepeats(true);
    }

    public void install(JComponent c) {
        this.component = c;
        c.setBorder(this);
        timer.start();
    }

    public void uninstall() {
        if (component != null) {
            component.setBorder(null); // или верни предыдущий бордер
        }
        timer.stop();
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        phase += 1.0f;                  // скорость движения точек
        if (phase >= dashLength * 2) phase -= dashLength * 2;
        if (component != null) component.repaint();
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float[] dash = {dashLength, dashLength};
        BasicStroke stroke = new BasicStroke(
                thickness,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10f,
                dash,
                phase              // ← вот здесь магия: сдвиг фазы создаёт "бег"
        );

        g2.setStroke(stroke);
        g2.setColor(color);

        // Рисуем прямоугольник без внутреннего заполнения
        g2.draw(new Rectangle2D.Float(x + thickness / 2f, y + thickness / 2f,
                w - thickness, h - thickness));

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    public static void main(String[] args) {
        final MarchingAntsBorder ants = new MarchingAntsBorder(
                new Color(0, 120, 255),   // синий
                3,                        // толщина
                6,                        // длина штриха/пробела
                80                        // скорость (меньше = быстрее)
        );

        JButton btnNext = new JButton("Next");
        ants.install(btnNext);

// через 8 секунд убираем
        final Timer stopTimer = new Timer(8000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ants.uninstall();
            }
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JLabel("Example Marching Ants Border"),BorderLayout.PAGE_START);
        frame.getContentPane().add(btnNext,BorderLayout.CENTER);
//        frame.setSize(300, 400);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}