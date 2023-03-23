package obuchenie.testing.junit4.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: tor
 * Date: 31.01.23
 * Time: 19:22
 * дополнение, которое позволит извлекать тестовые данные и ожидаемые результаты выполнения функции из внешнего файла
 * implement interface TestRule
 */
public class FileSourceRuleImp implements TestRule {
    List<TestData> data = new ArrayList<TestData>();
    InputStream inputStream;

    public FileSourceRuleImp(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String o;
                while ((o = reader.readLine()) != null) {
                    String[] split = o.split(" ");
                    int num1 = Integer.parseInt(split[0]);
                    int num2 = Integer.parseInt(split[1]);
                    int sum = Integer.parseInt(split[2]);
                    data.add(new TestData(num1, num2, sum));
                }
                base.evaluate();
                inputStream.close();
            }
        };
    }
}
