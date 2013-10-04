package com.tor.db.simplePolled.impDBPool;

import com.tor.db.simplePolled.DBPool;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.09.13
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class DBPoolCache implements DBPool {
    private PGPoolingDataSource source;

    DBPoolCache(String host, String database, String user, String password) {
        source = new PGPoolingDataSource();
        source.setDataSourceName("A Data Source");
        source.setServerName(host);
        source.setDatabaseName(database);
        source.setUser(user);
        source.setPassword(password);
        source.setMaxConnections(20);//Максимальное значение
        source.setInitialConnections(20);//Сколько соединений будет сразу открыто
    }

    @Override
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public void putConnection(Connection connection) throws SQLException {
        connection.close();
    }
}


