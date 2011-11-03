package com.tor.mock_test;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 15:22:14
 * ����������� ������� �������� ServiceClass, ������� �������� ���� �����: runService().
 * ����� service ��������� ������ Collaborator, ����������� ������� ��������� ICollaborator.
 * ���� ����� ���������� � ���������� ������ Collaborator: executeJob().
 * Collaborator - ��� �����, ������� �� ������ ����������� ��������������� �������.
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
