package com.tor.mock_test;

import com.agical.rmock.extension.junit.RMockTestCase;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 18:52:30
 * To change this template use File | Settings | File Templates.
 */
public class ServiceClassRMockTest extends RMockTestCase {
    private ServiceClass_CNA serviceClass;
    private Collaborator_NDC collaborator;

    public void setUp() {
        /**создать (как минимум) массив объектов, содержащий реальные значения параметров, передаваемых в конструктор
         * класса Collaborator. Также возможно (для ясности) включить class-types массив типов,
         * принимаемых конструктором, и передать этот массив, так же как и только что описанный массив объектов,
         * в качестве параметров для создания экземпляра фиктивного объекта Collaborator.
         * */
        serviceClass = new ServiceClass_CNA();
        Object[] objectArray = new Object[]{new Integer(5), "exampleString"};
        collaborator =
                (Collaborator_NDC) intercept(Collaborator_NDC.class, objectArray, "mockCollaborator");
    }

    /**
     * Использование jMock и RMock для имитации конкретного класса с конструктором не по умолчанию
     * <p/>
     * Причина неудачного выполнения заключается в том, что jMock не может создать жизнеспособный фиктивный объект
     * из определения класса, в котором нет конструктора без аргументов.
     * Единственным способом создания экземпляра объекта Collaborator является предоставление двух аргументов.
     * Вам теперь придется найти способ предоставления аргументов в процесс создания экземпляра фиктивного объекта для
     * достижения аналогичного эффекта. Именно поэтому используется RMock.
     */
    public void testRunServiceAndReturnFals_NotDefConstructor() {

        /**
         * Вызывается метод executeJob() фиктивного объекта collaborator. На этом этапе фиктивный метод находится в 
         * состоянии record, то есть, вы просто определяете вызовы методов, которые он будет ожидать при выполнении.
         * Соответственно фиктивный объект записывает ожидаемые результаты. Следующая строка - это уведомление
         * фиктивному объекту при появлении метода executeJob() возвратить строковое значение failure.
         *  Следовательно, используя RMock, вы устанавливаете ожидаемый результат простым вызовом метода вне фиктивного
         *  объекта (и передавая все параметры, которые могут понадобиться), затем изменяете этот ожидаемый результат
         * для подстройки всех возвращаемых типов соответствующим образом. */
        collaborator.executeJob();
        modify().returnValue("failure");
        /**startVerification() RMock для перевода фиктивного объекта Collaborator в состояние ready. Фиктивный
         * объект теперь готов для использования в классе ServiceClass в качестве реального объекта.
         * Метод абсолютно необходим и должен вызываться, для того чтобы избежать ошибок инициализации теста.
         * */
        startVerification();
        boolean rezult = serviceClass.runService(collaborator);
        assertFalse(rezult);
    }
}
