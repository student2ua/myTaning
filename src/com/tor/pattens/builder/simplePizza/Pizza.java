package com.tor.pattens.builder.simplePizza;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.03.2010
 * Time: 21:36:16
 * To change this template use File | Settings | File Templates.
 */
public class Pizza {
    private static final Logger log = Logger.getLogger(Pizza.class);
   private String dough = "";
	private String sauce = "";
	private String topping = "";

	public void setDough(String dough) {
		this.dough = dough;
	}

	public void setSauce(String sauce) {
		this.sauce = sauce;
	}

	public void setTopping(String topping) {
		this.topping = topping;
	}
}
