package com.tor.db;

import com.sun.rowset.JdbcRowSetImpl;
import oracle.jdbc.internal.StructMetaData;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.rowset.OracleRowSetListenerAdapter;
import oracle.jdbc.rowset.OracleWebRowSet;
import oracle.sql.StructDescriptor;
import org.intellij.lang.annotations.Language;
import org.junit.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.JdbcRowSet;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.09.11
 * Time: 13:03
 */
public class JDBCSample extends Assert {
    private static final String JDBC_XPSERVER = "java:MyDB333";
    //    private static final String JDBC_XPSERVER = "java:comp/env/jdbc/xpserver";
    private static Connection connection = null;
    private static DataSource ds;

    @BeforeClass
    public static void setUp() throws Exception {
        ds = initDS();
        connection = ds.getConnection();
    }

    private DataSource initDSJNDI() throws SQLException, NamingException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        properties.put(Context.PROVIDER_URL, "dev:1099");
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        OracleConnectionPoolDataSource pds = new OracleConnectionPoolDataSource();
        /*  pds.setServerName("xpserver");
        pds.setPortNumber(1521);
        pds.setDatabaseName("accoi");*/
        pds.setURL("jdbc:oracle:thin:@ann:1521:acco2018");
        pds.setUser("APP");
        pds.setPassword("APP");
        InitialContext ct = new InitialContext(properties);
        ct.bind(JDBC_XPSERVER, pds);
        ct.close();

        //--------------
        /* OracleDataSource rez=new OracleDataSource();
        *//*rez.setURL("xpserver:1521:accoi");
        rez.setUser("APP");
        rez.setPassword("APP");*//*
        InitialContext context=new InitialContext();
        context.bind("jdbc/xpserver",rez);*/
        InitialContext context = new InitialContext();
        return (DataSource) context.lookup(JDBC_XPSERVER);
    }

    private static DataSource initDS() throws SQLException, NamingException {

        OracleConnectionPoolDataSource pds = new OracleConnectionPoolDataSource();
        /*  pds.setServerName("xpserver");
        pds.setPortNumber(1521);
        pds.setDatabaseName("accoi");*/
        pds.setURL("jdbc:oracle:thin:@192.168.31.245:1521:acco245");
        pds.setUser("APP");
        pds.setPassword("APP");

        //--------------
        /* OracleDataSource rez=new OracleDataSource();
        *//*rez.setURL("xpserver:1521:accoi");
        rez.setUser("APP");
        rez.setPassword("APP");*//*
        InitialContext context=new InitialContext();
        context.bind("jdbc/xpserver",rez);*/

        return pds;
    }

    @Test
    public void testDatabaseMetaData() {
        DatabaseMetaData dbmd = null;

        try {
            dbmd = connection.getMetaData();
        } catch (SQLException se) {
            System.out.println("We got an exception while getting the metadata:check the connection.");
            fail(se.toString());
        }

        String username = null;
        try {
            username = dbmd.getUserName();
        } catch (SQLException se) {
            System.out.println("We got an exception while getting the username:check the connection.");
            fail(se.toString());
        }

        String url = null;
        try {
            url = dbmd.getURL();
        } catch (SQLException se) {
            System.out.println("We got an exception while getting the URL:check the connection.");
            fail(se.toString());
        }
        System.out.println("You are connected to '" + url + "' with user name '" + username + "'");

        try {
            //   System.out.println("getDatabaseMajorVersion() - "+dbmd.getDatabaseMajorVersion());
//            System.out.println("getDatabaseMinorVersion() - "+dbmd.getDatabaseMinorVersion());
            System.out.println("getDatabaseProductName() " + dbmd.getDatabaseProductName());
            System.out.println("getDatabaseProductVersion() " + dbmd.getDatabaseProductVersion());
            System.out.println("---------------------------");
            System.out.println("getDriverName() " + dbmd.getDriverName());
            System.out.println("getDriverVersion() " + dbmd.getDriverVersion());
            System.out.println("---------------------------");
            System.out.println("getIdentifierQuoteString() " + dbmd.getIdentifierQuoteString());
            System.out.println("---------------------------");
            System.out.println("getMaxConnections() " + dbmd.getMaxConnections());
            System.out.println("getMaxStatements() " + dbmd.getMaxStatements());
            System.out.println("getMaxTablesInSelect() " + dbmd.getMaxTablesInSelect());
            System.out.println("getExtraNameCharacters() " + dbmd.getExtraNameCharacters());
            System.out.println("getDefaultTransactionIsolation() " + getNameByTransactionIsolation(dbmd.getDefaultTransactionIsolation()));

            System.out.println("---------------------------");
            System.out.println("getNumericFunctions() " + dbmd.getNumericFunctions());
            System.out.println("getStringFunctions() " + dbmd.getStringFunctions());
            System.out.println("getSQLKeywords() " + dbmd.getSQLKeywords());
            System.out.println("getSystemFunctions() " + dbmd.getSystemFunctions());
            System.out.println("getTimeDateFunctions() " + dbmd.getTimeDateFunctions());

        } catch (SQLException se) {
            System.out.println("We got an exception while getting the URL:check the connection.");
            se.printStackTrace();
            fail();
        }

    }

    private String getNameByTransactionIsolation(int intTransactionIsolation) {
        String rez = "";
        switch (intTransactionIsolation) {
            case Connection.TRANSACTION_READ_UNCOMMITTED: {
                rez = "TRANSACTION_READ_UNCOMMITTED";
                break;
            }
            case Connection.TRANSACTION_READ_COMMITTED: {
                rez = "TRANSACTION_READ_COMMITTED";
                break;
            }
            case Connection.TRANSACTION_REPEATABLE_READ: {
                rez = "TRANSACTION_REPEATABLE_READ";
                break;
            }
            case Connection.TRANSACTION_SERIALIZABLE: {
                rez = "TRANSACTION_SERIALIZABLE";
                break;
            }
            default:
                rez = "Error value " + intTransactionIsolation;
        }
        return rez;
    }

    @Test
    @Ignore
    /** сначала выполнить создание
     * http://betteratoracle.com/posts/32-passing-arrays-of-record-types-between-oracle-and-java
     * https://books.google.com.ua/books?id=T0GvgQYG070C&pg=PA1074&lpg=PA1074&dq=StructMetaData+java+example&source=bl&ots=ggY1gXQI5X&sig=26iq23fXRfDRiB1Rzc8iXKy4w18&hl=ru&sa=X&ei=FnwmVd7LNIW7swHM2oGADg&ved=0CFQQ6AEwCA#v=onepage&q&f=false*/
    public void testStructMetaData() throws SQLException {
        String sql = "create type student as object (id number, name varchar2(20)," +
                "height float, dob date, picture BLOB,adress1 address_t, address2 ref address_t)";
        StructDescriptor structDescriptor = StructDescriptor.createDescriptor("STUDENT", connection);
        StructMetaData structMetaData = (StructMetaData) structDescriptor.getMetaData();
        int count = structMetaData.getLocalColumnCount();
        for (int i = 1; i < count; i++) {
            StringBuffer buffer = new StringBuffer(i + ") ");
            buffer.append(structMetaData.getColumnTypeName(i)).append(" ");
            buffer.append(structMetaData.getColumnType(i)).append(" ");
            buffer.append(structMetaData.getOracleColumnClassName(i)).append(" ");
            buffer.append(structMetaData.isSearchable(i));
            System.out.println(buffer.toString());
        }
    }

    @Test
    /** http://ramj2ee.blogspot.com/2014/08/jdbc-jdbcrowset-demo.html#.VSZs8F2sVeM */
    public void testJdbcRowSet() throws SQLException {
        JdbcRowSet rowSet = null;

        /*javax.sql.rowset.RowSetFactory rowSetFactory=javax.sql.rowset.RowSetProvider.newFactory();
         rowSet=rowSetFactory.createJdbcRowSet();*/

        rowSet = new JdbcRowSetImpl(connection);
        @Language("SQL")
        final String cmd = "SELECT * FROM dct_local.week_type";
        rowSet.setCommand(cmd);
        rowSet.execute();
        while (rowSet.next()) {
            System.out.println(rowSet.getInt(1) + "\t" + rowSet.getString(2));
        }

    }

    @Test   //https://docs.oracle.com/cd/E11882_01/java.112/e16548/jcrowset.htm#JJDBC28636
    public void testRowSet() throws SQLException {
          /*javax.sql.rowset.RowSetFactory rowSetFactory=javax.sql.rowset.RowSetProvider.newFactory();
         rowSet=rowSetFactory.createJdbcRowSet();*/
        RowSet rowSet = new JdbcRowSetImpl(connection);
//        rowSet.setUrl("jdbc:oracle:oci:@");
//        rowSet.setUsername("SCOTT");
//        rowSet.setPassword("TIGER");
//        rowSet.setCommand("SELECT empno, ename, sal FROM emp");
        rowSet.addRowSetListener(new RowSetListener() {
            @Override
            public void rowSetChanged(RowSetEvent event) {
            }

            @Override
            public void rowChanged(RowSetEvent event) {
            }

            @Override
            public void cursorMoved(RowSetEvent event) {
            }
        });

        rowSet.addRowSetListener(new OracleRowSetListenerAdapter() {
            @Override
            public void rowChanged(RowSetEvent rowSetEvent) {
                super.rowChanged(rowSetEvent);
            }
        });
        @Language("SQL")
        final String cmd = "SELECT humanid, lastname FROM human.human WHERE humanid = ?";
        rowSet.setCommand(cmd);
        // setting the employee number input parameter for employee named "KING"
        rowSet.setInt(1, 7839);
        rowSet.execute();
        // going to the first row of the rowset
        rowSet.beforeFirst();

        while (rowSet.next()) {
            System.out.println(rowSet.getInt(1));
            System.out.println(rowSet.getString(2));
        }
        /**
         * Make rowset updatable
         */
        rowSet.setReadOnly(false);
/**
 * Inserting a row in the 5th position of the rowset.
 */
// moving the cursor to the 5th position in the rowset
        if (rowSet.absolute(5)) {
            rowSet.moveToInsertRow();
            rowSet.updateInt(1, 193);
            rowSet.updateString(2, "Ashok");
            rowSet.updateInt(3, 7200);

            // inserting a row in the rowset
            rowSet.insertRow();

            // Synchronizing the data in RowSet with that in the database.
//            rowSet.acceptChanges (); ???

        }
    }

    @Test   //https://docs.oracle.com/cd/E11882_01/java.112/e16548/jcrowset.htm#JJDBC28636
    public void testWebRowSet() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT * FROM employee.employee");
        OracleWebRowSet wset = new OracleWebRowSet();
        wset.populate(rset);
        try {
            // Create a java.io.Writer object
            FileWriter out = new FileWriter("xml.out");
            // Now generate the XML and write it out
            wset.writeXml(out);
        } catch (IOException exc) {
            System.out.println("Couldn't construct a FileWriter");
        }
        System.out.println("XML output file generated.");

// Create a new OracleWebRowSet for reading from XML input
        OracleWebRowSet wset2 = new OracleWebRowSet();

// Use Oracle JAXP SAX parser
        System.setProperty("javax.xml.parsers.SAXParserFactory", "oracle.xml.jaxp.JXSAXParserFactory");

        try {
            // Use the preceding output file as input
            FileReader fr = new FileReader("xml.out");

            // Now read XML stream from the FileReader
            wset2.readXml(fr);
        } catch (IOException exc) {
            System.out.println("Couldn't construct a FileReader");
        }
    }

    /**
     * see https://stackoverflow.com/questions/25607985/get-inserted-row-to-oracle-with-java
     */
    @Test
    public void testReturnIdOnInsert() throws SQLException {
        Statement stmt = connection.createStatement();
        final String sql = "INSERT INTO DCT_COMMON.ACADEMICDEGREE  VALUES (DCT_COMMON.ACADEMICDEGREE_SEQ.NEXTVAL ,'Test 3 return id')";
        final int rezUdate = stmt.executeUpdate(
                sql,
//                Statement.RETURN_GENERATED_KEYS);Недопустимый тип столбца: getLong not implemented for class oracle.jdbc.driver.T4CRowidAccessor
                new String[]{"ID"});

//        ResultSet rs = stmt.getGeneratedKeys();
//        rs.next();
//        long pk = rs.getLong("ID");
        long pk = 0;
        if (rezUdate > 0) {

            // getGeneratedKeys() returns result set of keys that were auto generated
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            // if resultset has data, get the primary key value of last inserted record
            if (null != generatedKeys && generatedKeys.next()) {
                pk = generatedKeys.getLong(1);
            }
        }
        Assert.assertNotNull(pk);
        System.out.println("pk = " + pk);
    }

    @AfterClass
    public static void tearDown() throws Exception {
      /*  InitialContext initialContext = new InitialContext();
        initialContext.unbind(JDBC_XPSERVER);
        initialContext.close();*/
        if (connection != null) {
            connection.close();
        }
    }
}
