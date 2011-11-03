package tests;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 18.07.11
 * Time: 21:02
 * To change this template use File | Settings | File Templates.
 */
public class ConnectDBTest extends TestCase {
    public static final String URL_ORACLE_PREDICATE = "jdbc:oracle:thin:@";
    public static final String URL_ORACLE = "xpserver:1521:accoi";
    public static final String DB_USER1_LOGIN = "app";
    public static final String DB_USER1_PASSWORD = "app";
    public static final String DB_USER2_LOGIN = "acco";
    public static final String DB_USER2_PASSWORD = "account";

    DatabaseMetaData dbmd = null;

    public void testConnectionDirect_Oracle() {
        Connection con = null;
        Properties cPrpp = new Properties();
        cPrpp.put("user", DB_USER1_LOGIN);
        cPrpp.put("password", DB_USER1_PASSWORD);

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection(URL_ORACLE_PREDICATE + URL_ORACLE, cPrpp);
            assertNotNull(con);
            dbmd = con.getMetaData();
            System.out.println("Connection to " + dbmd.getDatabaseProductName() + " " + dbmd.getDatabaseProductVersion() + " successful.\n");
        } catch (SQLException e) {
            fail(e.toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {//
                }
            }
        }

    }

    /**
     * CREATE USER APP PASSWORD 'APP' DB_USER1_LOGIN, DB_USER1_PASSWORD
     */
    public void testConnectionDirect_H2() {
        Connection con = null;
        Properties cPrpp = new Properties();
        cPrpp.put("user", DB_USER1_LOGIN);
        cPrpp.put("password", DB_USER1_PASSWORD);
        try {
            Class.forName("org.h2.Driver");

            con = DriverManager.getConnection("jdbc:h2:~/test", cPrpp);
            assertNotNull(con);
            dbmd = con.getMetaData();
            System.out.println("Connection to " + dbmd.getDatabaseProductName() + " " + dbmd.getDatabaseProductVersion() + " successful.\n");
        } catch (SQLException e) {
            fail(e.toString());
        } catch (ClassNotFoundException e) {
            fail(e.toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {//
                }
            }
        }
    }

    /**
     * see http://www.enterprisedb.com/docs/en/8.4/jdbc/Postgres_Plus_Advanced_Server_JDBC_Connector_Guide-12.htm#TopOfPage
     * <p/>
     * -- Role: app
     * -- DROP ROLE app;
     * CREATE ROLE app LOGIN
     * ENCRYPTED PASSWORD 'md5fe63fea7be31b0200b496d08bc6b517d'
     * NOSUPERUSER INHERIT CREATEDB NOCREATEROLE;
     * 
     */
    public void testConnectionDirect_Postgres() {
        Connection con = null;
        Properties cPrpp = new Properties();
        cPrpp.put("user", DB_USER1_LOGIN);
        cPrpp.put("password", DB_USER1_PASSWORD);

        try {
            Class.forName("org.postgresql.Driver");
            //  DriverManager.registerDriver(new org.postgresql.Driver());
            con = DriverManager.getConnection("jdbc:postgresql://192.168.0.101:5432/acco", cPrpp);
            assertNotNull(con);
            dbmd = con.getMetaData();
            System.out.println("Connection to " + dbmd.getDatabaseProductName() + " " + dbmd.getDatabaseProductVersion() + " successful.\n");
        } catch (SQLException e) {
            fail(e.toString());
        } catch (ClassNotFoundException e) {
            fail(e.toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {//
                }
            }
        }
    }
}
