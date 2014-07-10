package com.tor.jpa;

import com.tor.service.HumanService;
import config.DBUnitConfig;
import junit.framework.Assert;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * User: tor
 * Date: 09.07.14
 * Time: 18:08
 * //        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), expectedData)
 */
public class HumanTest extends DBUnitConfig {
    private HumanService service= new HumanService();
    private EntityManager em = Persistence.createEntityManagerFactory("DBUnitEx").createEntityManager();

    public HumanTest(String name) {
        super(name);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com/tor/jpa/human/human-data.xml")
        );
    }

    @Test
    public void testGetAll() throws Exception {
        List<Human> humans = service.getAll();
        IDataSet exData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(
                        "com/tor/jpa/human/human-data.xml"
                )
        );
        IDataSet actualData = tester.getConnection().createDataSet();
        Assertion.assertEquals(exData, actualData);
        Assert.assertEquals(exData.getTable("human").getRowCount(), humans.size());
    }

    @Test
    public void testSave() throws Exception {
        Human human = new Human();
        human.setFirstName("Lula");
        human.setLastName("Wet");
        service.save(human);

        IDataSet expData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com/tor/jpa/human/human-data-save.xml")
        );
        IDataSet actualData = tester.getConnection().createDataSet();
        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expData, actualData, "human", ignore);

    }
    @Test
    public void testDelete() throws Exception {
        Human human = new Human();
        human.setFirstName("Alex");
        human.setLastName("Cvas");
        service.delete(human);

        IDataSet expData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com/tor/jpa/human/human-data-delete.xml")
        );
        IDataSet actualData = tester.getConnection().createDataSet();
        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expData, actualData, "human", ignore);

    }
}
