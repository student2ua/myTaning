package obuchenie.dbunit.habr;

import junit.framework.TestCase;
import obuchenie.dbunit.habr.User;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.10.11
 * Time: 16:51
 */
public class UserTest extends TestCase {
    private IDatabaseTester tester = null;

    public void setUp() throws Exception {
        super.setUp();
        instantiate();
    }

    // @Before
    public void instantiate() throws Exception {
        //Creating databse server instance
//        tester = new JdbcDatabaseTester("org.h2.Driver", "jdbc:h2:~/test", "sa", "");//enbeded
        tester = new JdbcDatabaseTester("org.h2.Driver", "jdbc:h2:tcp://localhost/~/test", "sa", "");
//        tester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:" + UUID.randomUUID().toString(), "sa", "");

        //Creating tables
        tester.getConnection().getConnection().prepareStatement("CREATE SEQUENCE IF NOT EXISTS SEQU START WITH 0").execute();
        tester.getConnection().getConnection().prepareStatement("CREATE SEQUENCE IF NOT EXISTS SEQU2 START WITH 0").execute();
        tester.getConnection().getConnection().prepareStatement("CREATE SEQUENCE IF NOT EXISTS SEQU3 START WITH 0").execute();
        tester.getConnection().getConnection().prepareStatement("CREATE MEMORY TABLE  IF NOT EXISTS TOKENS\n" +
                "( " +
//                id integer NOT NULL DEFAULT nextval('history_id_seq') PRIMARY KEY);
                "ID INT NOT NULL DEFAULT nextval('SEQU') PRIMARY KEY, " +
                " WORD LONGVARCHAR NOT NULL\n" +
                " )").execute();//TEMPORARY
        tester.getConnection().getConnection().prepareStatement("CREATE MEMORY  TABLE IF NOT EXISTS ORIGINAL_STRINGS(ID INT NOT NULL DEFAULT nextval('SEQU2') PRIMARY KEY, STRINGS LONGVARCHAR NOT NULL,DATE TIMESTAMP NOT NULL)").execute();
        tester.getConnection().getConnection().prepareStatement("CREATE MEMORY  TABLE IF NOT EXISTS LINKS(ID INT  NOT NULL DEFAULT nextval('SEQU3') PRIMARY KEY,TOKEN_ID INT NOT NULL,ORIGINAL_STRING_ID INT NOT NULL)").execute();
        //Setting DATA_FACTORY, so DBUnit will know how to work with specific HSQLDB data types
        tester.getConnection().getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        //  tester.getConnection().getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        //Getting dataset for database initialization E:\project\MyProject\treeTable\src\obuchenie\dbunit\UserTest.java
        System.out.println(this.getClass().getClassLoader().getResource(".").getPath());
        System.out.println(this.getClass().getClassLoader().getResource("obuchenie/dbunit/habr/template_set.xml").getPath());
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("obuchenie/dbunit/habr/template_set.xml"));
        assertNotNull(dataSet);
        //Initializing database
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    //  @Test
    public void testLogRequestStringTest() throws Exception {
        System.out.println("UserTest.testLogRequestStringTest");
        assertNotNull(tester);
        User man = new User(tester.getConnection().getConnection());
        try {
            man.logRequestString("Hello, world!");
        } catch (SQLException e) {
            fail(e.toString());
        }
//        E:\project\MyProject\treeTable\src\obuchenie\dbunit\check_set.xml

        // FlatXmlDataSet flatXmlDataSetBuilder= new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("obuchenie/dbunit/check_set.xml"));
        File file = new File(this.getClass().getClassLoader().getResource("").getFile() + "obuchenie/dbunit/habr/check_set.xml");
        assertTrue(file.isFile());
        assertTrue(file.canRead());
        FlatXmlDataSet flatXmlDataSetBuilder = new FlatXmlDataSetBuilder().build(file);
        assertNotNull(flatXmlDataSetBuilder);
        ITable template = flatXmlDataSetBuilder.getTable("tokens");
        assertNotNull(template);
        System.out.print(""+tester.getConnection().createDataSet().getTable("tokens"));
        ITable actual = DefaultColumnFilter.includedColumnsTable(tester.getConnection().createDataSet().getTable("tokens"), template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
    }

}
