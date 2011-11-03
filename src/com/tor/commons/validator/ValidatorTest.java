package com.tor.commons.validator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 30.03.2010
 * Time: 20:27:46
 * To change this template use File | Settings | File Templates.
 */
public class ValidatorTest extends TestCase {
    private static final Logger log = Logger.getLogger(ValidatorTest.class);

    public ValidatorTest(String s) {
        super(s);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ValidatorTest("testGenericValidator"));
        return suite;
    }

    public void testGenericValidator() {
        assertTrue(GenericValidator.isBlankOrNull(""));
        assertTrue(GenericValidator.isBlankOrNull(null));
        assertTrue(GenericValidator.isDate("20050929_1836", "yyyyMMdd_HHmm", true));
        assertTrue(GenericValidator.isInRange(2, 1, 10));   //значение , мин , мах
        assertTrue(GenericValidator.minLength("проверка длины", 5));
    }
}
