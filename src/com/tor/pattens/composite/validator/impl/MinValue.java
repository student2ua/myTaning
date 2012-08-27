package com.tor.pattens.composite.validator.impl;

import com.tor.pattens.composite.validator.Constraint;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.03.12
 * Time: 14:30
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * минимальное значение
 */
public class MinValue implements Constraint<Integer> {
    private Integer minVal;

    public MinValue(Integer minVal) {
        this.minVal = minVal;
    }

    @Override
    public boolean isValid(Integer integer) {
        return minVal < integer;
    }

    @Override
    public String getMessage() {
        return "Значение меньше " + minVal;
    }
}
