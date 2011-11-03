package com.tor.db;

import com.tor.thread.SwingWorker;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 14.07.2010
 * Time: 16:05:08
 * Работа с базой через пул
 */
public class DBSupport_OracleConnectionPool {
    private static final Logger log = Logger.getLogger(DBSupport_OracleConnectionPool.class);
    OracleConnectionPoolDataSource pds;
    OracleDataSource ds;
    private Statement opc;
    private String dbUrl = "jdbc:oracle:thin:@" + "ann:1521:accoi";
    private String dbUser = "APP";
    private String dbPassword = "APP";

    public DBSupport_OracleConnectionPool() {
        try {
            pds = new OracleConnectionPoolDataSource();
            pds.setURL(dbUrl);
            pds.setUser(dbUser);
            pds.setPassword(dbPassword);
             // for Test
            int maxThread=200;
            StopWatch watch = new StopWatch();
            watch.start();
            for (int i = 1; i < maxThread; i++) {
                new DBPooledLoadWorker().start();
            }
            watch.stop();
            System.out.println("watch = " + watch);
            // for test END
        } catch (SQLException e) {
            e.printStackTrace();
            //    log.error(e);
        }
    }

    public static void closeEverything(ResultSet rs, Statement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("все равно что то отвалилось при закрытии \n" + e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("все равно что то отвалилось при закрытии \n" + e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("все равно что то отвалилось при закрытии \n" + e);
            }
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = pds.getPooledConnection().getConnection();
            // con= pds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return con;
    }

    private class DBPooledLoadWorker extends SwingWorker {

        public Object construct() {
            Connection con = null;
            ResultSet rs = null;
            try {
                con = getConnection();
                // con.setReadOnly(true);
                rs = con.createStatement().executeQuery("select count (id) from entrant.entrant");
               if(rs.next()) System.out.println("rs = " + rs.getObject(1));
            } catch (SQLException e) {
                e.printStackTrace();
                //    log.error(e);
            } finally {
                DBSupport_OracleConnectionPool.closeEverything(rs, null, con);
            }
            return rs;
        }

        public void start() {
            super.start();
            System.out.println("работу начал");
        }

        public void finished() {
            System.out.println("работу закончил");
        }
    }

    public static void main(String[] args) {
        DBSupport_OracleConnectionPool dbSupport = new DBSupport_OracleConnectionPool();


    }
    /* public static void main(String[] args) {
        DBSupport dbSupport = new DBSupport();
        Connection con = null;
        ResultSet rs = null;
        try {
            StopWatch watch = new StopWatch();
            watch.start();

            con = dbSupport.getConnection();
            con.setReadOnly(true);
            rs = con.createStatement().executeQuery("select * from entrant.entrant");
            watch.stop();
            System.out.println("watch = " + watch);
            /*if(!rs.next()){
                System.out.println("почемуто ничего не прочитали");}*/
    /*    int col=rs.getMetaData().getColumnCount();
        while (rs.next()){
            Vector prnVector=new Vector(col);
            for(int prnInt=1;prnInt<col;prnInt++){
                prnVector.add(rs.getObject(prnInt));
            }
            System.out.println("prnVector = " + prnVector);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        //    log.error(e);
    } finally {
        DBSupport.closeEverything(rs, null, con);
    }*/

    /*  OracleConnectionPoolDataSource ds;
    private Statement opc;
   public DBSupport() {
        try {
            ds = new OracleConnectionPoolDataSource();
            ds.setURL(dbUrl);
            ds.setUser(dbUser);
            ds.setPassword(dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            //log.error(e);
        }
    }

    public OracleConnection getConnection() throws SQLException, Exception {
        OracleConnection conn = null;

        if (this.ds == null) {
            System.out.println("DataSource not set");
            throw new Exception("DataSource not set.");
        }

        try {
            System.out.println("get OracleConnection");
            conn = (OracleConnection) opc.getConnection();
            return conn;
        } catch (SQLException e) {
            System.out.println("DataSource not set" + e.toString());
            throw new SQLException("Database Problem. Detail is " + e.toString());
        }
    }*/
}
