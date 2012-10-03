package com.tor.pattens.composite.validator.impl;

import com.tor.pattens.composite.validator.Constraint;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.03.12
 * Time: 14:44
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 *
 * @pattern Composite
 */
public class ComplexConstraint<T> implements Constraint<T> {
    LinkedList<Constraint<T>> constraints = new LinkedList<Constraint<T>>();
    private Constraint<T> last = null;

    public void add(Constraint<T> tConstraint) {
        this.constraints.add(tConstraint);
    }

    @Override
    public boolean isValid(T t) {
        for (Constraint<T> constraint : constraints) {
            if (!constraint.isValid(t)) {
                last = constraint;
                return false;
            }
        }
        last = null;
        return true;
    }

    @Override
    public String getMessage() {
        return last==null?"Все в порядке":last.getMessage();
    }
}
