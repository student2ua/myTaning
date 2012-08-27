package com.tor.pattens.composite.validator.impl;

import com.tor.pattens.composite.validator.Constraint;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.03.12
 * Time: 14:36
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class MaxValue implements Constraint<Integer>{
    private Integer maxValue;

    public MaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public boolean isValid(Integer integer) {
        return maxValue > integer;
    }

    @Override
    public String getMessage() {
        return "Значение не должно быть больше чам "+maxValue;
    }
}
