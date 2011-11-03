package obuchenie.xmlmenu.exemple;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.04.2010
 * Time: 16:32:36
 * To change this template use File | Settings | File Templates.
 */
public class DemoActions extends AbstractAction {
    private static final Logger log = Logger.getLogger(DemoActions.class);

    public DemoActions() {
        super();
        putValue(Action.NAME, "DemoActions");
        putValue(Action.SHORT_DESCRIPTION, "пример использования Action");
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog((Component) e.getSource(), "Заглушка");
    }
}
