package com.tor.pattens.builder.simplePizza.concreteBuilder;

import com.tor.pattens.builder.simplePizza.PizzaBuilder;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 21:39:09
 * To change this template use File | Settings | File Templates.
 */
public class SpicyPizzaBuilder extends PizzaBuilder {
    private static final Logger log = Logger.getLogger(SpicyPizzaBuilder.class);

    public void buildDough() {
        pizza.setDough("pan baked");
    }

    public void buildSauce() {
        pizza.setSauce("hot");
    }

    public void buildTopping() {
        pizza.setTopping("pepperoni+salami");
    }
}
