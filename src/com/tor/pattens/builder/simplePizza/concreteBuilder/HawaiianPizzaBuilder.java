package com.tor.pattens.builder.simplePizza.concreteBuilder;

import com.tor.pattens.builder.simplePizza.PizzaBuilder;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 21:38:04
 * To change this template use File | Settings | File Templates.
 */
public class HawaiianPizzaBuilder extends PizzaBuilder {
    private static final Logger log = Logger.getLogger(HawaiianPizzaBuilder.class);
    public void buildDough() {
		pizza.setDough("cross");
	}

	public void buildSauce() {
		pizza.setSauce("mild");
	}

	public void buildTopping() {
		pizza.setTopping("ham+pineapple");
	}
}
