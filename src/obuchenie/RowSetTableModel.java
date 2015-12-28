package obuchenie;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.spi.SyncFactory;
import javax.sql.rowset.spi.SyncProvider;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Properties;

/**
 * User: tor
 * Date: 28.12.15
 * Time: 16:22
 * Database Programming with JDBC and Java, Second Edition
 * By George Reese
 * ISBN: 1-56592-616-1
 *
 * Publisher: O'Reilly
 */
public class RowSetTableModel extends AbstractTableModel implements RowSetListener {
    private RowSet rowSet = null;

    public RowSetTableModel(RowSet set) {
        super();
        rowSet = set;
        rowSet.addRowSetListener(this);
    }

    public void cursorMoved(RowSetEvent event) {
    }

    public Class getColumnClass(int column) {
        String cname;
        int type;

        try {
            ResultSetMetaData meta = rowSet.getMetaData();

            if (meta == null) {
                return null;
            }
            type = meta.getColumnType(column + 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return super.getColumnClass(column);
        }
        switch (type) {
            case Types.BIT: {
                cname = "java.lang.Boolean";
                break;
            }
            case Types.TINYINT: {
                cname = "java.lang.Byte";
                break;
            }
            case Types.SMALLINT: {
                cname = "java.lang.Short";
                break;
            }
            case Types.INTEGER: {
                cname = "java.lang.Integer";
                break;
            }
            case Types.BIGINT: {
                cname = "java.lang.Long";
                break;
            }
            case Types.FLOAT:
            case Types.REAL: {
                cname = "java.lang.Float";
                break;
            }
            case Types.DOUBLE: {
                cname = "java.lang.Double";
                break;
            }
            case Types.NUMERIC: {
                cname = "java.lang.Number";
                break;
            }
            case Types.DECIMAL: {
                cname = "java.math.BigDecimal";
                break;
            }
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR: {
                cname = "java.lang.String";
                break;
            }
            case Types.DATE: {
                cname = "java.sql.Date";
                break;
            }
            case Types.TIME: {
                cname = "java.sql.Time";
                break;
            }
            case Types.TIMESTAMP: {
                cname = "java.sql.Timestamp";
                break;
            }
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY: {
                cname = "byte[]";
                break;
            }
            case Types.OTHER:
            case Types.JAVA_OBJECT: {
                cname = "java.lang.Object";
                break;
            }
            case Types.CLOB: {
                cname = "java.sql.Clob";
                break;
            }
            case Types.BLOB: {
                cname = "java.ssql.Blob";
                break;
            }
            case Types.REF: {
                cname = "java.sql.Ref";
                break;
            }
            case Types.STRUCT: {
                cname = "java.sql.Struct";
                break;
            }
            default: {
                return super.getColumnClass(column);
            }
        }
        try {
            return Class.forName(cname);
        } catch (Exception e) {
            e.printStackTrace();
            return super.getColumnClass(column);
        }
    }

    public int getColumnCount() {
        try {
            ResultSetMetaData meta = rowSet.getMetaData();

            if (meta == null) {
                return 0;
            }
            return meta.getColumnCount();
        } catch (SQLException e) {
            return 0;
        }
    }

    public String getColumnName(int col) {
        try {
            ResultSetMetaData meta = rowSet.getMetaData();

            if (meta == null) {
                return null;
            }
            return meta.getColumnName(col + 1);
        } catch (SQLException e) {
            return "Error";
        }
    }

    public int getRowCount() {
        try {
            if (rowSet.last()) {
                return (rowSet.getRow());
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    public Object getValueAt(int row, int col) {
        try {
            if (!rowSet.absolute(row + 1)) {
                return null;
            }
            return rowSet.getObject(col + 1);
        } catch (SQLException e) {
            return null;
        }
    }

    public void rowChanged(RowSetEvent event) {
        try {
            int row = rowSet.getRow();

            if (rowSet.rowDeleted()) {
                fireTableRowsDeleted(row, row);
            } else if (rowSet.rowInserted()) {
                fireTableRowsInserted(row, row);
            } else if (rowSet.rowUpdated()) {
                fireTableRowsUpdated(row, row);
            }
        } catch (SQLException e) {
        }
    }

    public void rowSetChanged(RowSetEvent event) {
        fireTableStructureChanged();
    }

    public void setValueAt(Object value, int row, int column) {
        try {
            if (!rowSet.absolute(row + 1)) {
                return;
            }
            rowSet.updateObject(column + 1, value);
        } catch (SQLException e) {
        }
    }

    public static void main(String[] args) throws SQLException {
        java.util.Enumeration providers = SyncFactory.getRegisteredProviders();
        while (providers.hasMoreElements()) {
            SyncProvider o = (SyncProvider) providers.nextElement();
            System.out.println("SyncProvider = [" + o.getProviderID() + "]");
        }
        //JDBC 2.0 API + http://docs.oracle.com/javase/6/docs/api/javax/sql/rowset/CachedRowSet.html
        CachedRowSet rowSet = new CachedRowSetImpl();
        //----------------
        //-------- при ошибках ORA-00604 ORA-12705 --------
        //  -Doracle.jdbc.timezoneAsRegion=false   or
        // TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
        //TimeZone.setDefault(timeZone);

        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Properties cPrpp = new Properties();
        final String s = "ann:1521:acco2";
        cPrpp.put("user", "app");
        cPrpp.put("password", "app");

        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + s, cPrpp);

       /* jdbc 6 !!!!
       final Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM dct_local.subject");
        for (; rs.next(); ) {
            System.out.printf("% 3d %s%n", rs.getInt("id"), rs.getString("NAME") + rs.getString("SHORT_NAME"));
        }*/
        //----------------
       /* rowSet.setUrl("jdbc:oracle:thin:@" + s);
        rowSet.setUsername("app");
        rowSet.setPassword("app");*/
        //----------------
        rowSet.setCommand("select * from dct_local.subject");
        rowSet.execute(con);
       /* while (rowSet.next()) {
            System.out.printf("% 3d %s%n", rowSet.getInt("id"), rowSet.getString("name"));
        }*/

        // in tm addeded Listener
        RowSetTableModel tableModel = new RowSetTableModel(rowSet);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(new JTable(tableModel)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}