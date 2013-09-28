package com.tor.db.simplePolled;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.09.13
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public interface DBPool {
    Connection getConnection() throws SQLException;

    void putConnection(Connection connection) throws SQLException;
}
