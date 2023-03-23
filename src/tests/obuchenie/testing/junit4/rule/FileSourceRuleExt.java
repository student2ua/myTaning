package obuchenie.testing.junit4.rule;

import org.junit.rules.ExternalResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: tor
 * Date: 31.01.23
 * Time: 19:37
 * можно использовать базовый абстрактный класс ExternalResource
 */
public class FileSourceRuleExt extends ExternalResource {
    List<TestData> data = new ArrayList<TestData>();
    InputStream inputStream;

    public FileSourceRuleExt(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    protected void before() throws Throwable {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String o;
        while ((o = reader.readLine()) != null) {
            String[] split = o.split(" ");
            int num1 = Integer.parseInt(split[0]);
            int num2 = Integer.parseInt(split[1]);
            int sum = Integer.parseInt(split[2]);
            data.add(new TestData(num1, num2, sum));
        }
    }

    @Override
    protected void after() {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
