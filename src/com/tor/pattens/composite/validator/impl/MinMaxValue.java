package com.tor.pattens.composite.validator.impl;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.03.12
 * Time: 15:46
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class MinMaxValue extends ComplexConstraint<Integer>{
    public MinMaxValue(int min,int max) {
        add(new MinValue(min));
        add(new MaxValue(max));
    }
    public static boolean isValid(int min,int max,Integer val){
        return new MinMaxValue(min,max).isValid(val);
    }
}
