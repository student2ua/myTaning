package obuchenie;

import javax.swing.event.SwingPropertyChangeSupport;
import java.awt.*;

/**
 * User: tor
 * Date: 28.08.13
 * добавление к любой модели
 * <pre>
 * .addPropertyChangeListener( new PropertyChangeListener() {
 *
 * @Override public void propertyChange(PropertyChangeEvent evt) {
 * if (!"integer".equalsIgnoreCase(evt.getPropertyName())) return;</pre>
 */
public class SwingAndModelPropertyChange {
    private SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this);

    //--- model
    private Color color;
    private Integer integer;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        Color old = this.color;
        this.color = color;
        pcs.firePropertyChange("color", old, color);
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        Integer old = this.integer;
        this.integer = integer;
        pcs.firePropertyChange("integer", old, integer);
    }
}
