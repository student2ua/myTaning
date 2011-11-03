package com.tor.db;

import junit.framework.TestCase;
import oracle.jdbc.internal.StructMetaData;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.sql.StructDescriptor;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.09.11
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class JDBCSample extends TestCase {
    private static final String JDBC_XPSERVER = "java:MyDB333";
    //    private static final String JDBC_XPSERVER = "java:comp/env/jdbc/xpserver";
    private Connection connection = null;
    private DataSource ds;

    public void setUp() throws Exception {
        super.setUp();
        ds = initDS();

        connection = ds.getConnection();
    }

    private DataSource initDS() throws SQLException, NamingException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        properties.put(Context.PROVIDER_URL, "dev:1099");
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        OracleConnectionPoolDataSource pds = new OracleConnectionPoolDataSource();
        /*  pds.setServerName("xpserver");
        pds.setPortNumber(1521);
        pds.setDatabaseName("accoi");*/
        pds.setURL("jdbc:oracle:thin:@xpserver:1521:accoi");
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
            System.out.println("getDefaultTransactionIsolation() " + dbmd.getDefaultTransactionIsolation());
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

    public void testStructMetaData() {
        try {
            StructDescriptor structDescriptor = StructDescriptor.createDescriptor("DCT_LOCAL.SUBJECT", connection);
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
        } catch (SQLException e) {
            fail(e.toString());
        }

    }

    public void tearDown() throws Exception {
        super.tearDown();
        InitialContext initialContext = new InitialContext();
        initialContext.unbind(JDBC_XPSERVER);
        initialContext.close();
        if (connection != null) {
            connection.close();
        }
    }
}
