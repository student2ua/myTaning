package obuchenie.data;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.08.2008
 * Time: 19:42:36
 * To change this template use File | Settings | File Templates.
 */
public class DataToTestTest extends TestCase {
    public DataToTestTest(String testName) throws FileNotFoundException {
        super(testName);

    }

    public void uGetPersonCollection() throws FileNotFoundException {
        Collection c =  DataToTest.getPersonCollection();
        System.out.println("c = " + c);
        assertNotNull(c);
        Vector v= new Vector((Collection) c);
        assertTrue((v.get(0)instanceof Person));
        Person p= (Person) v.get(0);
        assertTrue(p.getFio().getLastName().equals("Фамилье1"));
        Person p2= (Person) DataToTest.getPersonCollection().toArray()[0];
        assertTrue(p2.getFio().getLastName().equals("Фамилье1"));
      // assertEquals();
    }

    public static Test suite() throws FileNotFoundException {
        TestSuite suite = new TestSuite();
        suite.addTest(new DataToTestTest("uGetPersonCollection"));


        return suite;
    }
}
