package com.tor;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.04.12
 * Time: 16:57
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class ExpressionUtilsTest extends TestCase {

    public void testCalculateExpression_Lvl_1() throws Exception {
        assertTrue(ExpressionUtils.calculateExpression("3+4").intValueExact() == 7);
    }

    public void testCalculateExpression_Lvl_2() throws Exception {
        assertTrue(ExpressionUtils.calculateExpression("3+4/2").intValueExact() == 5);
    }

    public void testCalculateExpression_Lvl_3() throws Exception {
        assertTrue(ExpressionUtils.calculateExpression("(3+5)/2").intValueExact() == 4);
    }

    public void testCalculateExpression_function() throws Exception {
        assertTrue(ExpressionUtils.calculateExpression("abs(-1)").intValueExact() == 1);
    }

    public void testSortingStation_function() throws Exception{
        System.out.println(ExpressionUtils.sortingStation("abs (-1/2) ", ExpressionUtils.MAIN_MATH_OPERATIONS));
       assertEquals(ExpressionUtils.sortingStation("abs (-1/2) ", ExpressionUtils.MAIN_MATH_OPERATIONS),"1 2 / - ABS");
    }
}
