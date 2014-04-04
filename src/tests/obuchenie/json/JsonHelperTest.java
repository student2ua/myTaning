package obuchenie.json;

import junit.framework.Assert;
import obuchenie.data.DataToTest;
import obuchenie.data.Person;
import org.junit.Test;

/**
 * User: tor
 * Date: 04.04.14
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class JsonHelperTest {
    private String s = null;

    @Test
    public void testToJSON() throws Exception {
        s = JsonHelper.toJSON(DataToTest.getPersonCollection().get(0));
        Assert.assertNotNull(s);
        System.out.println("s = " + s);
    }

    @Test
    public void testFromJSON() throws Exception {
        String s = "{\"person\":{\"age\":20,\"fio\":{\"firstName\":\"Р�РјРµС‡РєРѕ1\",\"lastName\":\"Р¤Р°РјРёР»СЊРµ1\",\"midllName\":\"РћС‚С‡РµСЃС‚РІРѕ1\"},\"sex\":true}}";
        Person p = JsonHelper.fromJSON(s, Person.class);
        Assert.assertNotNull(p);
        System.out.println("p = " + p);
    }
}
