package com.tor.pattens.visitor.simplePizza;

import com.tor.pattens.visitor.simplePizza.pizzaImpl.GodFather;
import com.tor.pattens.visitor.simplePizza.pizzaImpl.PizzaHut;
import com.tor.pattens.visitor.simplePizza.pizzaImpl.PopJohn;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 17:35:26
 * To change this template use File | Settings | File Templates.
 */
public class visitorMine {
    private static final Logger log = Logger.getLogger(visitorMine.class);

    public static void main(String[] args) {
        List pizzaList = new ArrayList();
        pizzaList.add(new PopJohn());
        pizzaList.add(new PizzaHut());
        pizzaList.add(new GodFather());
        Iterator it = pizzaList.iterator();
        System.out.println("How many pizza restaurants in this area?");
        while (it.hasNext()) {
            System.out.println(((Pizza) it.next()).order());
        }
        Dinner d = new Dinner();
        Pizza pza = d.getDinner();
        Visitor v = d.howto();
        v.visit(pza);
        System.out.println("\nWhich store for dinner?");
        System.out.println(v);
    }
}
