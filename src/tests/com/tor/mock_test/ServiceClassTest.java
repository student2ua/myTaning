package com.tor.mock_test;

import org.apache.log4j.Logger;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
//import org.jmock.cglib.MockObjectTestCase;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 17:48:54
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
     * Использование jMock для имитации интерфейсов
     * <p>Предположим, что предметом тестирования является утверждение, что метод runService() не выполнялся, или,
     * другими словами, что возвращенный Boolean-результат равен false. В этом случае имитируется
     * передаваемый в метод runService() объект ICollaborator для ожидания вызова его метода executeJob()
     * и возврата строки, отличной от "success". Таким образом, вы гарантируете,
     * что Boolean-строка false возвращается в тест.</p>
     */
    public void testRunServiceAndReturnFals() {
        mock.expects(once()).method("executeJob").will(returnValue("failure"));
        collaborator = (ICollaborator) mock.proxy();
        boolean rezult = serviceClass.runService(collaborator);
        assertFalse(rezult);
    }
    /** Использование jMock для имитации конкретного класса с конструктором по умолчанию
     * Предположим, что метод runService() в классе ServiceClass_CNA принимает только конкретные реализации класса 
     * Collaborator. Будет ли достаточно jMock для проверки того, что предыдущий тест выполнился успешно без изменения
     * ожидаемых результатов?
     * Во-первых, сигнатура метода runService() изменилась. Вместо приема интерфейса ICollaborator он принимает
     * теперь реализацию конкретного класса (класса Collaborator)
     * Во-вторых, изменился способ имитации класса Collaborator. CGLIB-библиотека jMock предоставляет возможность
     * имитировать конкретный класс. Дополнительный String-параметр для метода mock() jMock CGLIB используется
     * в качестве идентификатора создаваемого фиктивного объекта. При использовании jMock (и, конечно же, RMock)
     * уникальные идентификаторы необходимы для каждого имитируемого объекта в одном контрольном примере. Это верно
     * для фиктивных объектов, определенных в общем методе setUp() или в реальном методе test.
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
