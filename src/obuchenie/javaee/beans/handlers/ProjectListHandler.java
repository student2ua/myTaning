package obuchenie.javaee.beans.handlers;

import obuchenie.javaee.dao.ProjectDAO;
import obuchenie.javaee.exceptions.ProjectException;
import obuchenie.javaee.exceptions.ListHandlerException;
import obuchenie.javaee.util.ValueListHandler;
import obuchenie.javaee.vo.ProjectVO;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 20:24:02
 * To change this template use File | Settings | File Templates.
 */
public class ProjectListHandler extends ValueListHandler {
    private static final Logger log = Logger.getLogger(ProjectListHandler.class);
    private ProjectDAO dao = null;
    // ������������ ProjectTO ��� ������ ��� �����������
    // �������� ������
    private ProjectVO projectCriteria = null;

    // ������ ������� ��������� ProjectTO, �������������
    // �������� �������� ������ � ��������
    // ��������� ProjectTO ��� projectCriteria
    // � ����������� � ����� setCriteria()
    public ProjectListHandler(ProjectVO projectCriteria) throws ProjectException, ListHandlerException {
        try {
            this.projectCriteria = projectCriteria;
            this.dao = PSADAOFactory.getProjectDAO();
            executeSearch();
        } catch (Exception e) {
            // ���������� �������������� ��������, ������������� ListHandlerException
        }
    }

    public void setCriteria(ProjectVO projectCriteria) {
        this.projectCriteria = projectCriteria;
    }

    // ��������� �����. ������ ����� ������������ �������� ������,
    // ��������� ��������� �������� ������.
    // ������������ ��� ����������
    // ������ � ���������� �������.
    public void executeSearch() throws ListHandlerException {
        try {
            if (projectCriteria == null) {
                throw new ListHandlerException("Project Criteria required...");
            }
            List resultsList = dao.executeSelect(projectCriteria);
            setList(resultsList);
        } catch (Exception e) {
            // ���������� �������������� ��������, ������������� ListHandlerException
        }
    }
}
