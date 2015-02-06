package com.tor.db;

import oracle.jdbc.driver.OraclePreparedStatement;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 16.09.11
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public class ExmplSmtpCache {
    public static void main(String[] args) throws SQLException {
        OracleConnection connection = null;
        OraclePreparedStatement oraclePreparedStatement = null;
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL("jdbc:oracle:thin:@ann:1521:accotest");
        dataSource.setUser("APP");
        dataSource.setPassword("APP");
        String sql = "select id,login from UNIVERSITYAUDIT.TEACHERLOGIN";
        //1 - no caching
        connection = (OracleConnection) dataSource.getConnection();
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < 11; i++) {
            oraclePreparedStatement = (OraclePreparedStatement) connection.prepareStatement(sql);
            ResultSet resultSet = oraclePreparedStatement.executeQuery();
            resultSet.close();
            oraclePreparedStatement.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("non cached 10 execute " + (endTime - startTime));
        connection.close();

        //2 - execute caching
        connection = (OracleConnection) dataSource.getConnection();
        //set size 1-----------------------------------
        connection.setStatementCacheSize(1);
        //enable execute caching-----------------------
        connection.setExplicitCachingEnabled(true);
        startTime = System.currentTimeMillis();
        for (int i = 1; i < 11; i++) {
            oraclePreparedStatement = (OraclePreparedStatement) connection.getStatementWithKey("Stmt1");
            if (oraclePreparedStatement == null) {
                oraclePreparedStatement = (OraclePreparedStatement) connection.prepareStatement(sql);
            }
            ResultSet resultSet = oraclePreparedStatement.executeQuery();
            resultSet.close();
            oraclePreparedStatement.closeWithKey("Stmt1");
        }
        endTime = System.currentTimeMillis();
        System.out.println("10 executing with cache " + (endTime - startTime));
        connection.close();
    }

}
