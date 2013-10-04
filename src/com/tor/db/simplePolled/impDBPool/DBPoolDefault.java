package com.tor.db.simplePolled.impDBPool;

import com.tor.db.simplePolled.DBPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.09.13
 * Time: 12:15
 * классический, который на каждый запрос открывает соединение с сервером и после выполнения запроса закрывающий его
 */
public class DBPoolDefault implements DBPool {
    private String url, user, password;

    DBPoolDefault(String url, String user, String password) throws ClassNotFoundException {
        this.url = url;
        this.user = user;
        this.password = password;
        Class.forName("org.postgresql.Driver");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void putConnection(Connection connection) throws SQLException {
        connection.close();
    }
}


