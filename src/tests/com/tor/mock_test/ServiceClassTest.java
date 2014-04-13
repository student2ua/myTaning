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
     * »спользование jMock дл€ имитации интерфейсов
     * <p>ѕредположим, что предметом тестировани€ €вл€етс€ утверждение, что метод runService() не выполн€лс€, или,
     * другими словами, что возвращенный Boolean-результат равен false. ¬ этом случае имитируетс€
     * передаваемый в метод runService() объект ICollaborator дл€ ожидани€ вызова его метода executeJob()
     * и возврата строки, отличной от "success". “аким образом, вы гарантируете,
     * что Boolean-строка false возвращаетс€ в тест.</p>
     */
    public void testRunServiceAndReturnFals() {
        mock.expects(once()).method("executeJob").will(returnValue("failure"));
        collaborator = (ICollaborator) mock.proxy();
        boolean rezult = serviceClass.runService(collaborator);
        assertFalse(rezult);
    }
    /** »спользование jMock дл€ имитации конкретного класса с конструктором по умолчанию
     * ѕредположим, что метод runService() в классе ServiceClass_CNA принимает только конкретные реализации класса 
     * Collaborator. Ѕудет ли достаточно jMock дл€ проверки того, что предыдущий тест выполнилс€ успешно без изменени€
     * ожидаемых результатов?
     * ¬о-первых, сигнатура метода runService() изменилась. ¬место приема интерфейса ICollaborator он принимает
     * теперь реализацию конкретного класса (класса Collaborator)
     * ¬о-вторых, изменилс€ способ имитации класса Collaborator. CGLIB-библиотека jMock предоставл€ет возможность
     * имитировать конкретный класс. ƒополнительный String-параметр дл€ метода mock() jMock CGLIB используетс€
     * в качестве идентификатора создаваемого фиктивного объекта. ѕри использовании jMock (и, конечно же, RMock)
     * уникальные идентификаторы необходимы дл€ каждого имитируемого объекта в одном контрольном примере. Ёто верно
     * дл€ фиктивных объектов, определенных в общем методе setUp() или в реальном методе test.
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
