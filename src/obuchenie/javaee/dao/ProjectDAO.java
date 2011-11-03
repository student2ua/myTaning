package obuchenie.javaee.dao;

import obuchenie.javaee.vo.ProjectVO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 20:31:11
 * To change this template use File | Settings | File Templates.
 */
public class ProjectDAO {
    private static final Logger log = Logger.getLogger(ProjectDAO.class);

    final private String tableName = "PROJECT";

    // ������� select ���������� ����
    final private String fields = "project_id, name," +
            "project_manager_id, start_date, end_date, " +
            " started, completed, accepted, acceptedDate," +
            " customer_id, description, status";

    // �����, ��������������� ValueListHandler,
    // �������� �����.
    // ��. ������� Data Access Object ��� �������������� ����������.

    public List executeSelect(ProjectVO projCriteria) throws SQLException {

        PreparedStatement stmt = null;
        List list = null;
        Connection con = getConnection();
        StringBuffer selectStatement = new StringBuffer();
        selectStatement.append("SELECT " + fields +
                " FROM " + tableName + "where 1=1");

        // �������� �������������� ������� � ��������� where
        // � ����������� �� ��������, ��������� �
        // projCriteria
        if (projCriteria.projectId != null) {
            selectStatement.append(" AND PROJECT_ID = '" +
                    projCriteria.projectId + "'");
        }
        // ��������� � �������� ������ ���� � ��������� where

        try {
            stmt = con.prepareStatement(selectStatement.toString());
            stmt.setString(1, resourceID);
            ResultSet rs = stmt.executeQuery();
            list = prepareResult(rs);
            stmt.close();
        }
        finally {
            con.close();
        }
        return list;
    }

    private List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while (rs.next()) {
            int i = 1;
            ProjectVO proj = new ProjectVO(rs.getString(i++));
            proj.projectName = rs.getString(i++);
            proj.managerId = rs.getString(i++);
            proj.startDate = rs.getDate(i++);
            proj.endDate = rs.getDate(i++);
            proj.started = rs.getBoolean(i++);
            proj.completed = rs.getBoolean(i++);
            proj.accepted = rs.getBoolean(i++);
            proj.acceptedDate = rs.getDate(i++);
            proj.customerId = rs.getString(i++);
            proj.projectDescription = rs.getString(i++);
            proj.projectStatus = rs.getString(i++);
            list.add(proj);

        }
        return list;
    }

}
