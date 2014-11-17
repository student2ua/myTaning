package obuchenie.testing.junit4.practicle;

import junit.framework.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * User: tor
 * Date: 14.05.14
 * Time: 16:08
 * Theories — параметризирует тестовый метод, а не конструктор. Данные помечаются с помощью @DataPoints и @DataPoint,
 * тестовый метод — с помощью @Theory.
 */
@RunWith(Theories.class)
public class TheoryTest2DataPoints {
    @DataPoints
    public static Object[][] isEmptyData = new Object[][]{
            {"", true},
            {" ", false},
            {"some string", false},
    };

    @Theory
    public void testIsEmp(Object... o) {
        String s = (String) o[0];
        Assert.assertEquals(s.isEmpty(), o[1]);

    }

}
