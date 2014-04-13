package tests.com.tor.mock_test;

import com.tor.mock_test.Collaborator;
import com.tor.mock_test.ICollaborator;
import com.tor.mock_test.ServiceClass;
import com.tor.mock_test.ServiceClass_CNA;
import org.apache.log4j.Logger;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
//import org.jmock.cglib.MockObjectTestCase;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 17:48:54
 * To change this template use File | Settings | File Templates.
 */
public class ServiceClassTest extends MockObjectTestCase {
    private static final Logger log = Logger.getLogger(ServiceClassTest.class);
    private ServiceClass serviceClass;
    private ICollaborator collaborator;
    private Mock mock;
    private Mock mockCollaborator;
    private Collaborator collaboratorImp;

    public void setUp() {
        serviceClass = new ServiceClass();
        mock = new Mock(ICollaborator.class);
    }

    /**
     * ������������� jMock ��� �������� �����������
     * <p>�����������, ��� ��������� ������������ �������� �����������, ��� ����� runService() �� ����������, ���,
     * ������� �������, ��� ������������ Boolean-��������� ����� false. � ���� ������ �����������
     * ������������ � ����� runService() ������ ICollaborator ��� �������� ������ ��� ������ executeJob()
     * � �������� ������, �������� �� "success". ����� �������, �� ������������,
     * ��� Boolean-������ false ������������ � ����.</p>
     */
    public void testRunServiceAndReturnFals() {
        mock.expects(once()).method("executeJob").will(returnValue("failure"));
        collaborator = (ICollaborator) mock.proxy();
        boolean rezult = serviceClass.runService(collaborator);
        assertFalse(rezult);
    }
    /** ������������� jMock ��� �������� ����������� ������ � ������������� �� ���������
     * �����������, ��� ����� runService() � ������ ServiceClass_CNA ��������� ������ ���������� ���������� ������ 
     * Collaborator. ����� �� ���������� jMock ��� �������� ����, ��� ���������� ���� ���������� ������� ��� ���������
     * ��������� �����������?
     * ��-������, ��������� ������ runService() ����������. ������ ������ ���������� ICollaborator �� ���������
     * ������ ���������� ����������� ������ (������ Collaborator)
     * ��-������, ��������� ������ �������� ������ Collaborator. CGLIB-���������� jMock ������������� �����������
     * ����������� ���������� �����. �������������� String-�������� ��� ������ mock() jMock CGLIB ������������
     * � �������� �������������� ������������ ���������� �������. ��� ������������� jMock (�, ������� ��, RMock)
     * ���������� �������������� ���������� ��� ������� ������������ ������� � ����� ����������� �������. ��� �����
     * ��� ��������� ��������, ������������ � ����� ������ setUp() ��� � �������� ������ test.
     *  */
    public void testRunServiceAndReturnFalse_ClassNotArgument() {
        ServiceClass_CNA serviceClass = new ServiceClass_CNA();

        mockCollaborator = mock(Collaborator.class, "mockCollaborator");
        mockCollaborator.expects(once()).method("executeJob").will(returnValue("failure"));

        collaboratorImp = (Collaborator) mockCollaborator.proxy();
        boolean rezult = serviceClass.runService(collaboratorImp);
        assertFalse(rezult);
    }
}
