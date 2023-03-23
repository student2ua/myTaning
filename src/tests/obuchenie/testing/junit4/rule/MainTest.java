package obuchenie.testing.junit4.rule;

import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * User: tor
 * Date: 31.01.23
 * Time: 19:45
 * https://habr.com/ru/company/otus/blog/713908/
 */
public class MainTest {
    @Rule
    public FileSourceRuleExt rule=new FileSourceRuleExt(ClassLoader.getSystemResourceAsStream("test.dat"));
    @Test
    public void testSum(){
        for (TestData testData : rule.data) {
            Assert.assertTrue((testData.numOwn+testData.numTwo)==testData.sum);
        }

    }
}
