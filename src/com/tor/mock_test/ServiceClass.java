package com.tor.mock_test;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 15:22:14
 * Тестируемым классом является ServiceClass, который содержит один метод: runService().
 * Метод service принимает объект Collaborator, реализующий простой интерфейс ICollaborator.
 * Один метод реализован в конкретном классе Collaborator: executeJob().
 * Collaborator - это класс, который вы должны имитировать соответствующим образом.
 */
public class ServiceClass {
    private static final Logger log = Logger.getLogger(ServiceClass.class);

    public boolean runService(ICollaborator collaborator) {
        if ("success".equals(collaborator.executeJob())) {
            return true;
        } else
            return false;
    }
}
