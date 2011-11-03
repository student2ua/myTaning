package com.tor.pattens.builder.simplePizza;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 21:36:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class PizzaBuilder {
    private static final Logger log = Logger.getLogger(PizzaBuilder.class);
    protected Pizza pizza;

    public Pizza getPizza() {
        return pizza;
    }

    public void createNewPizzaProduct() {
        pizza = new Pizza();
    }

    public abstract void buildDough();

    public abstract void buildSauce();

    public abstract void buildTopping();
}
