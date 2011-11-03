package com.tor.db;

import junit.framework.TestCase;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.09.11
 * Time: 14:40
 * Oracle® Database JDBC Developer's Guide and Reference 10g Release 1 (10.1) Part Number B10979-01
 */
public class ExamplePooledConnection extends TestCase {


    /**
     * For details on the connection cache API, see the Javadoc for OracleDataSource and OracleConnectionCacheManager.
     */
    public void testImplicitConnectionCache() {
// Example to show binding of OracleDataSource to JNDI,
// then using implicit connection cache
        Connection conn = null;

        Context ctx = new InitialContext(ht);
        OracleDataSource ods = null;
        try {
            ods = new OracleDataSource();
            // Set DataSource properties
            ods.setUser("Scott");
            ods.setImplicitCachingEnabled(true);    // Turns on caching
            ctx.bind("MyDS", ods);
            try {

                // ...
                // Retrieve DataSource from the InitialContext
                ods = (OracleDataSource) ctx.lookup("MyDS");
                // Transparently create cache and retrieve connection
                conn = ods.getConnection();
                //....
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.close();  // return connection to the cache
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            // close datasource and clean up the cache
            if (ods != null) ods.close();
        }
    }

    public void testConnectionAttributeExample() {
        java.util.Properties connAttr = new java.util.Properties();
        connAttr.setProperty("TRANSACTION_ISOLATION", "SERIALIZABLE");
        connAttr.setProperty("CONNECTION_TAG", "JOE'S_CONNECTION");

        // retrieve connection that matches attributes
        Connection conn = ds.getConnection(connAttr);
        // Check to see which attributes weren't matched
        Object unmatchedProp = ((OracleConnection) conn).getUnMatchedConnectionAttributes();
        if (unmatchedProp != null) {
            // apply attributes to the connection
            ((OracleConnection) conn).applyConnectionAttributes(connAttr);
        }
        // verify whether conn contains property after apply attributes
        connProp = ((OracleConnection) conn).getConnectionAttributes();
        listProperties(connProp);

    }

    public void testConnectionPropertyExample() {
        OracleDataSource ds = (OracleDataSource) ctx.lookup("...");
        java.util.Properties prop = new java.util.Properties();
        prop.setProperty("MinLimit", "5");     // the cache size is 5 at least
        prop.setProperty("MaxLimit", "25");
        prop.setProperty("InitialLimit", "3"); // create 3 connections at startup
        prop.setProperty("InactivityTimeout", "1800");    //  seconds
        prop.setProperty("AbandonedConnectionTimeout", "900");  //  seconds
        prop.setProperty("MaxStatementsLimit", "10");
        prop.setProperty("PropertyCheckInterval", "60"); // seconds

        ds.setConnectionCacheProperties(prop);  // set properties
        Connection conn = ds.getConnection();
        conn.dosomework();
        java.util.Properties propList = ds.getConnectionCacheProperties();  // retrieve

    }

    public void tesConnectionCacheManagerUse() {
// Get singleton ConnectionCacheManager instance
        OracleConnectionCacheManager occm = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
        String cacheName = "foo";  // Look for a specific cache
        // Use Cache Manager to check # of available connections
        // and active connections
        System.out.println(occm.getNumberOfAvailableConnections(cacheName)
                " connections are available in cache " + cacheName);

        System.out.println(occm.getNumberOfActiveConnections(cacheName)
                + " connections are active");
        // Refresh all connections in cache
        occm.refreshCache(cacheName,
                OracleConnectionCacheManager.REFRESH_ALL_CONNECTIONS);
        // Reinitialize cache, closing all connections
        java.util.Properties newProp = new java.util.Properties();
        newProp.setProperty("MaxLimit", "50");
        occm.reinitializeCache(cacheName, newProp);

    }
    
}
