package com.tor.pattens.builder.simplePizza;

import com.tor.pattens.builder.simplePizza.concreteBuilder.HawaiianPizzaBuilder;
import com.tor.pattens.builder.simplePizza.concreteBuilder.SpicyPizzaBuilder;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 21:41:47
 * To change this template use File | Settings | File Templates.
 */
public class BuilderExample {
    private static final Logger log = Logger.getLogger(BuilderExample.class);

    public static void main(String[] args) {
        Cook cook = new Cook();
        PizzaBuilder hawaiianPizzaBuilder = new HawaiianPizzaBuilder();
        PizzaBuilder spicyPizzaBuilder = new SpicyPizzaBuilder();

        cook.setPizzaBuilder(hawaiianPizzaBuilder);
        cook.constructPizza();

        Pizza pizza = cook.getPizza();

        System.out.println("pizza = " + pizza);
        cook.setPizzaBuilder(spicyPizzaBuilder);
        cook.constructPizza();
        Pizza pizza2=cook.getPizza();
        System.out.println("pizza2 = " + pizza2);
    }
}
