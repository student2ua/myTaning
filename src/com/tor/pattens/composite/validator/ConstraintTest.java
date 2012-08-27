package com.tor.pattens.composite.validator;

import com.tor.pattens.composite.validator.impl.MaxValue;
import com.tor.pattens.composite.validator.impl.MinMaxValue;
import com.tor.pattens.composite.validator.impl.MinValue;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.03.12
 * Time: 15:36
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class ConstraintTest extends TestCase {
    public void testMinValue() {
        MinValue minValue = new MinValue(5);
        String msg = null;
        if (!minValue.isValid(3)) {
            msg = minValue.getMessage();
        }
        assertNotNull(msg);
        msg = null;
        if (!minValue.isValid(7)) {
            msg = minValue.getMessage();
        }
        assertNull(msg);
    }

    public void testMaxValue() {
        MaxValue maxValue = new MaxValue(5);
        String msg = null;
        if (!maxValue.isValid(7)) {
            msg = maxValue.getMessage();
        }
        assertNotNull(msg);
        msg = null;
        if (!maxValue.isValid(3)) {
            msg = maxValue.getMessage();
        }
        assertNull(msg);
    }
    public void testMinMaxValue() {

        assertFalse(MinMaxValue.isValid(2,5,100000));

        String msg = null;

        MinMaxValue minMaxValue=new MinMaxValue(2,5);
        if (!minMaxValue.isValid(10000)) {
            msg = minMaxValue.getMessage();
        }
        assertNotNull(msg);
    }
}
