package obuchenie.testing.junit4.practicle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 08.01.13
 * Time: 13:03
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://rdafbn.blogspot.gr/2012/11/junit4-parameterized-and-theories.html
 */
@RunWith(Parameterized.class)
public class ParameterizedTest {

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                /* Sport Nation year totWinners */
                {"basket", "usa", 2002, 5,},
                {"soccer", "argentina", 2003, 2},
                {"tennis", "spain", 2004, 10},
                {"chess", "ireland", 2005, 0},
                {"eatingbananas", "italy", 2006, 20}
        });
    }

    private final String sport;
    private final String nation;
    private final int year;
    private final int totWinners;

    public ParameterizedTest(String sport, String nation, int year, int totWinners) {
        this.sport = sport;
        this.nation = nation;
        this.year = year;
        this.totWinners = totWinners;
    }

    @Test
    public void test() {
        Assert.assertTrue(isDataCorrect(sport, nation, year, totWinners));
    }

    private boolean isDataCorrect(String sport, String nation, int year, int totWinners) {
        System.out.println("sport = [" + sport + "], nation = [" + nation + "], year = [" + year + "], totWinners = [" + totWinners + "]");
        return true;
    }
}
