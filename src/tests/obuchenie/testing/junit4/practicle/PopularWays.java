package obuchenie.testing.junit4.practicle;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * User: tor
 * Date: 10.04.14
 * Time: 13:43
 * To change this template use File | Settings | File Templates.
 */
public class PopularWays {
    Calculator calculator = new Calculator();
    /**
     * in  JUnit 3.*
     *
     * @Test public void throwsExceptionWhenNegativeNumbersAreGiven() {
     * try {
     * calculator.add("-1,-2,3");
     * fail("Should throw an exception if one or more of given numbers are negative");
     * } catch (Exception e) {
     * assertThat(e)
     * .isInstanceOf(IllegalArgumentException.class)
     * .hasMessage("negatives not allowed: [-1, -2]");
     * }
     * }
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsExceptionWhenNegativeNumbersAreGiven() {
        // arrange
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(equalTo("negatives not allowed: [-1, -2]"));
        // act

        calculator.add("-1,-2,3");
    }

    @Test (expected = IllegalArgumentException.class)
    public void throwsExceptionWhenNegativeNumbersAreGiven2() {
        // act
        calculator.add("-1,-2,3");
    }

    private class Calculator {
        public BigDecimal add(String s) {
            if (s.equals("-1,-2,3"))
                throw new IllegalArgumentException("negatives not allowed: [-1, -2]");
            return BigDecimal.valueOf(1);
        }
    }
}
