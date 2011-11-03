package com.tor;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 30.03.2009
 * Time: 15:35:53
 * To change this template use File | Settings | File Templates.
 */
public class WorkDB {
    private static final Logger log = Logger.getLogger(WorkDB.class);

    Connection con = null;

    public void setCon(Connection con) {
        this.con = con;
    }

    public ArrayList execUniversalQuery(String sqlZapros, Class rezultClass) {
        ArrayList rezult = new ArrayList();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlZapros);
            final Field[] fields = rezultClass.getFields();
            final Class[] params_of_get = {String.class};
            while (rs.next()) {

                Object newRow =
                        rezultClass.getConstructor(null).newInstance(null);
                for (int it = 0; it < fields.length; it++) {
                    String fildName = fields[it].getName();
                    if ("int".equals(fildName)) {
                        fildName = "Int";
                    } else {
                        fildName = fildName.substring(fildName.lastIndexOf(".") + 1);
                    }
                    final Object[] arg_of_get = {fildName};
                    fields[it].set(
                            newRow,
                            ResultSet.class.getDeclaredMethod("get" + fildName, params_of_get).
                                    invoke(rs, arg_of_get)
                    );
                }
                rezult.add(newRow);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            log.error(e);
        } catch (InvocationTargetException e) {
            //e.printStackTrace();
            log.error(e);
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
            log.error(e);
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            log.error(e);
        } catch (InstantiationException e) {
            //e.printStackTrace();
            log.error(e);
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                //e.printStackTrace();
                log.error(e);
            }
        }
        return rezult;
    }
    
}
