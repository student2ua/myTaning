package obuchenie.data;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.08.2008
 * Time: 18:33:44
 * To change this template use File | Settings | File Templates.
 */
public class DataToTest {
    private static final Logger log = Logger.getLogger(DataToTest.class);

    public static Collection getPersonCollection() {
        Collection collectionPerson = new ArrayList();
        collectionPerson.add(new Person(new FIO("Фамилье1", "Отчество1", "Имечко1"), new Integer(20), true));
        collectionPerson.add(new Person(new FIO("Фамилье1", "Отчество1", "Имечко2"), new Integer(21), false));
        collectionPerson.add(new Person(new FIO("Фамилье1", "Отчество1", "Имечко3"), new Integer(19), true));
        collectionPerson.add(new Person(new FIO("Фамилье1", "Отчество2", "Имечко1"), new Integer(18), false));
        collectionPerson.add(new Person(new FIO("Фамилье1", "Отчество2", "Имечко2"), new Integer(22), false));
        collectionPerson.add(new Person(new FIO("Фамилье1", "Отчество2", "Имечко3"), new Integer(16), false));
        collectionPerson.add(new Person(new FIO("Фамилье2", "Отчество3", "Имечко1"), new Integer(32), true));
        collectionPerson.add(new Person(new FIO("Фамилье2", "Отчество3", "Имечко2"), new Integer(17), true));
        collectionPerson.add(new Person(new FIO("Фамилье2", "Отчество3", "Имечко3"), new Integer(25), false));
        collectionPerson.add(new Person(new FIO("Фамилье3", "Отчество1", "Имечко1"), new Integer(24), true));

        return (Collection) collectionPerson;
    }

    public static Collection getBuildingCollection() {
        Collection collectionBuilding = new ArrayList();
        collectionBuilding.add(new Building("# 1",
                new Floor[]{
                        new Floor(new Integer(1), new Room[]{
                                new Room(new Integer(1), new Integer(3)),
                                new Room(new Integer(2), new Integer(3)),
                                new Room(new Integer(3), new Integer(3))}),
                        new Floor(new Integer(2), new Room[]{
                                new Room(new Integer(4), new Integer(3)),
                                new Room(new Integer(5), new Integer(3)),
                                new Room(new Integer(6), new Integer(3))})}));
        collectionBuilding.add(new Building("# 2",
                new Floor[]{
                        new Floor(new Integer(1), new Room[]{
                                new Room(new Integer(11), new Integer(3)),
                                new Room(new Integer(12), new Integer(3)),
                                new Room(new Integer(13), new Integer(3))}),
                        new Floor(new Integer(2), new Room[]{
                                new Room(new Integer(14), new Integer(3)),
                                new Room(new Integer(15), new Integer(3)),
                                new Room(new Integer(16), new Integer(3))})}));
    return collectionBuilding;
}
}
