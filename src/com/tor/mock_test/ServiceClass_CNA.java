package com.tor.mock_test;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 18:29:43
 * ClassNotArgument -класс с конструктором по умолчанию
 */
public class ServiceClass_CNA {
    public ServiceClass_CNA() {
    }

    public boolean runService(Collaborator collaborator) {
        if ("success".equals(collaborator.executeJob())) {
            return true;
        } else {
            return false;
        }
    }
     public boolean runService(Collaborator_NDC collaborator) {
        if ("success".equals(collaborator.executeJob())) {
            return true;
        } else {
            return false;
        }
    }
}
