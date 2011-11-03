package obuchenie.fuuuzbuzz;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 14.01.2011
 * Time: 12:53:34
 * To change this template use File | Settings | File Templates.
 */
public class FizzBuzzGenerator {
    private static final Logger log = Logger.getLogger(FizzBuzzGenerator.class);

    public static void main(String[] args) {
        //при начале с 0 получаем
        for (int i = 1; i < 101; i++) {
            StringBuffer rezult = new StringBuffer();
            if (i % 3 == 0) {
                rezult.append("Fizz");
            }
            if (i % 5 == 0) {
                rezult.append("Buzz");
            }
            if (0 == rezult.length()) {
                rezult.append(i);
            }
            System.out.println("rezult = " + rezult.toString());
        }
    }
}
