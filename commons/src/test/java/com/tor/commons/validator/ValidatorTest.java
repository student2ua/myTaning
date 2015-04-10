package com.tor.commons.validator;

import org.apache.commons.validator.GenericValidator;
import org.junit.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 30.03.2010
 * Time: 20:27:46
 * test fore org.apache.commons.validator package
 */
public class ValidatorTest {

    @org.junit.Test
    public void testGenericValidator() {
        Assert.assertTrue(GenericValidator.isBlankOrNull(""));
        Assert.assertTrue(GenericValidator.isBlankOrNull(null));
        Assert.assertTrue(GenericValidator.isDate("20050929_1836", "yyyyMMdd_HHmm", true));
        Assert.assertTrue(GenericValidator.isInRange(2, 1, 10));   //значение , мин , мах
        Assert.assertTrue(GenericValidator.minLength("проверка длины", 5));
    }
}
