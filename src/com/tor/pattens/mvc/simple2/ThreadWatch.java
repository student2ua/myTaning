package com.tor.pattens.mvc.simple2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * User: tor
 * Date: 17.11.14
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class ThreadWatch {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ThreadWatch().create();
            }
        });
    }

    private void create() {
        JFrame f = new JFrame("Thread Watch");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Model model = new Model();
        View view = new View(model);
        Control control = new Control(model);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(view, BorderLayout.CENTER);
        panel.add(control, BorderLayout.SOUTH);
        f.add(panel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        new Thread(model).start();
    }
}

class Control extends JPanel implements ActionListener, ChangeListener, ItemListener {

    private static final String RESET = "Reset";
    private final JCheckBox fillCheck = new JCheckBox("Fill");
    private Model model;

    public Control(Model model) {
        this.model = model;
        this.addButton(RESET);
        this.add(fillCheck);
        fillCheck.addItemListener(this);
        JLabel label = new JLabel("Rate (Hz):");
        this.add(label);
        JSpinner speed = new JSpinner(new SpinnerNumberModel(Model.RATE, 1, 50, 1));
        speed.setEditor(new JSpinner.NumberEditor(speed, "0"));
        speed.setMaximumSize(speed.getPreferredSize());
        speed.addChangeListener(this);
        this.add(speed);
    }

    private void addButton(String name) {
        JButton button = new JButton(name);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        add(button);
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (RESET.equals(cmd)) {
            model.reset();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        Number value = (Number) spinner.getValue();
        model.getTimer().setDelay(1000 / value.intValue());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        boolean state = e.getStateChange() == ItemEvent.SELECTED;
        if (source == fillCheck) {
            model.setFill(state);
        }
    }
}

class View extends JPanel implements Observer {

    private static final int WIDE = 600;
    private static final int HIGH = WIDE * Model.ASPH / Model.ASPW;
    private Model model;

    public View(Model model) {
        this.model = model;
        this.setPreferredSize(new Dimension(WIDE, HIGH));
        model.addObserver(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setComposite(AlphaComposite.Src);
        g.drawImage(model.getImage(), 0, 0,
                this.getWidth(), this.getHeight(), null);
    }

    public void update(Observable o, Object arg) {
        this.repaint();
    }
}

/**
 * A stochastic model that simulates the accumulation of image data.
 *
 * The model runs on a separate thread. A javax.swing.Timer is used for interim
 * updates so that the actionPerformed() method executes on the
 * event-dispatching thread. As this relies on the Observable's call to
 * update(), avoid calling notifyObservers() from any other thread. Note also
 * that image is final [JLS 17.5].
 */
class Model extends Observable implements ActionListener, Runnable {

    public static final int RATE = 25; // 25 Hz
    public static final int ASPW = 5;  // Aspect ratio width
    public static final int ASPH = 3;  // Aspect ratio height
    private static final int WIDE = 256;
    private static final int HIGH = WIDE * ASPH / ASPW;
    private static final Random random = new Random();
    private static final Color[] clut = new Color[16];

    static {
        for (int i = 0; i < clut.length; i++) {
            int v = i * 16;
            clut[i] = new Color(v, v, v);
        }
    }

    private final Timer timer = new Timer(1000 / RATE, this);
    private final BufferedImage image = new BufferedImage(WIDE, HIGH, BufferedImage.TYPE_INT_ARGB);
    private final Graphics2D g2d = image.createGraphics();
    private final WritableRaster raster = image.getRaster();
    private final int[] ia = new int[4];
    private boolean fill;
    private int index;

    public void run() {
        reset();
        while (true) {
            next();
            Thread.yield();
        }
    }

    private void next() {
        if (fill) {
            g2d.setPaint(clut[index++]);
            if (index == clut.length) {
                index = 0;
            }
            g2d.fillRect(0, 0, WIDE, HIGH);
        } else {
            double dx = Math.abs(random.nextGaussian());
            double dy = Math.abs(random.nextGaussian());
            int x = (int) (dx * WIDE / 2.123d);
            int y = (int) (dy * HIGH / 2.123d);
            if (x < WIDE && y < HIGH) {
                raster.getPixel(x, y, ia);
                adjust(0);
                adjust(1);
                adjust(2);
                adjust(3);
                raster.setPixel(x, y, ia);
            }
        }
    }

    private void adjust(int i) {
        ia[i]++;
        if (ia[i] > 255) {
            ia[i] = 128;
        }
    }

    public synchronized BufferedImage getImage() {
        return image;
    }

    public synchronized Timer getTimer() {
        return timer;
    }

    public synchronized void setFill(boolean fill) {
        this.fill = fill;
        reset();
    }

    public synchronized void reset() {
        timer.stop();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, WIDE, HIGH);
        g2d.setComposite(AlphaComposite.Src);
        Color c = Color.getHSBColor(random.nextFloat(), 0.9f, 0.9f);
        float[] fa = c.getRGBComponents(null);
        g2d.setPaint(new Color(fa[0], fa[1], fa[2], 0.75f));
        g2d.fillRect(0, 0, WIDE, HIGH);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            this.setChanged();
            this.notifyObservers();
        }
    }
}