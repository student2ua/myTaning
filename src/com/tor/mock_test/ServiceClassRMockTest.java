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
        /**������� (��� �������) ������ ��������, ���������� �������� �������� ����������, ������������ � �����������
         * ������ Collaborator. ����� �������� (��� �������) �������� class-types ������ �����,
         * ����������� �������������, � �������� ���� ������, ��� �� ��� � ������ ��� ��������� ������ ��������,
         * � �������� ���������� ��� �������� ���������� ���������� ������� Collaborator.
         * */
        serviceClass = new ServiceClass_CNA();
        Object[] objectArray = new Object[]{new Integer(5), "exampleString"};
        collaborator =
                (Collaborator_NDC) intercept(Collaborator_NDC.class, objectArray, "mockCollaborator");
    }

    /**
     * ������������� jMock � RMock ��� �������� ����������� ������ � ������������� �� �� ���������
     * <p/>
     * ������� ���������� ���������� ����������� � ���, ��� jMock �� ����� ������� �������������� ��������� ������
     * �� ����������� ������, � ������� ��� ������������ ��� ����������.
     * ������������ �������� �������� ���������� ������� Collaborator �������� �������������� ���� ����������.
     * ��� ������ �������� ����� ������ �������������� ���������� � ������� �������� ���������� ���������� ������� ���
     * ���������� ������������ �������. ������ ������� ������������ RMock.
     */
    public void testRunServiceAndReturnFals_NotDefConstructor() {

        /**
         * ���������� ����� executeJob() ���������� ������� collaborator. �� ���� ����� ��������� ����� ��������� � 
         * ��������� record, �� ����, �� ������ ����������� ������ �������, ������� �� ����� ������� ��� ����������.
         * �������������� ��������� ������ ���������� ��������� ����������. ��������� ������ - ��� �����������
         * ���������� ������� ��� ��������� ������ executeJob() ���������� ��������� �������� failure.
         *  �������������, ��������� RMock, �� �������������� ��������� ��������� ������� ������� ������ ��� ����������
         *  ������� (� ��������� ��� ���������, ������� ����� ������������), ����� ��������� ���� ��������� ���������
         * ��� ���������� ���� ������������ ����� ��������������� �������. */
        collaborator.executeJob();
        modify().returnValue("failure");
        /**startVerification() RMock ��� �������� ���������� ������� Collaborator � ��������� ready. ���������
         * ������ ������ ����� ��� ������������� � ������ ServiceClass � �������� ��������� �������.
         * ����� ��������� ��������� � ������ ����������, ��� ���� ����� �������� ������ ������������� �����.
         * */
        startVerification();
        boolean rezult = serviceClass.runService(collaborator);
        assertFalse(rezult);
    }
}
