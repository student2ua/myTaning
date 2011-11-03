package com.tor.pattens.visitor.simplePizza;

import com.tor.pattens.visitor.simplePizza.pizzaImpl.GodFather;
import com.tor.pattens.visitor.simplePizza.pizzaImpl.PizzaHut;
import com.tor.pattens.visitor.simplePizza.pizzaImpl.PopJohn;
import com.tor.pattens.visitor.simplePizza.visitorImpl.ByDelivery;
import com.tor.pattens.visitor.simplePizza.visitorImpl.ByEatin;
import com.tor.pattens.visitor.simplePizza.visitorImpl.ByPickup;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:16:40
 * To change this template use File | Settings | File Templates.
 */
public class Dinner {
    private static final Logger log = Logger.getLogger(Dinner.class);

    public static Pizza getDinner() {
        Pizza tmp = null;
        int switcher = (int) (Math.random() * 3);
        switch (switcher) {
            case 0:
                tmp = new GodFather();
                break;
            case 1:
                tmp = new PizzaHut();
                break;
            case 2:
                tmp = new PopJohn();
                break;
        }
        return tmp;
    }

    public static Visitor howto() {
        Visitor tmp = null;
        switch ((int) (Math.random() * 3)) {
            case 0:
                tmp = new ByDelivery();
                break;
            case 1:
                tmp = new ByEatin();
                break;
            case 2:
                tmp = new ByPickup();
                break;
        }
        return tmp;
    }
}
