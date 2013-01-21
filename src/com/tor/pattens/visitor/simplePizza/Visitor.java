package com.tor.pattens.visitor.simplePizza;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 16:52:31
 * пиццу - съесть на месте, забрать с собой или заказать доставку
 */
public interface Visitor {
    public void visit(Pizza pizza);
}
