package com.tor.text;

import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.01.2009
 * Time: 18:33:54
 * MessageFormat ������������ ��� ������������ "�����������" ��������� � ����������. ��� �������� MessageFormat �
 * ������������ ����������� ��������� � ������� ���������� ������� �����������
 * (�������� ���������� PrintStream.printf()). �������������� ������� ����������� � �������� ������. ��� ��������������
 * MessageFormat ��������� � �������� ���������� ������ �������� � ����������� �� � ������ �����. ���� ��� �����������
 * ������� ��� ��������������� ��������������� �������, �� �� ������������, � �������� ���� ��� ��������������� �������
 * �� ������� ������ �� ����������� ��� ����. ��� �������������� ������� � ������ ����������� ��� ����� toString() ���
 * ���� �� ��������� ����� ����������.
 * <p/>
 * ������ ��������������� ������� ���������: {0[,<���_�������>[,<���������_�������>]]}, � ���������� ������� ��������
 * �������������� ���������. <���_�������> - �������� ���������� ������������� ��� ��������������, ��������� ���������
 * ��������: number, date, time, choice. <���������_�������> - ��������� �������������� ����������� ��� �������
 * ����������, ��� ���� � ������� ����� ������� ���� ���� �� ����������� �������� (short, medium, long, full) ����
 * ��������������� ������ ����), ���������� ��� ����� ���� ����������� ������ (integer, currency, percent) ����
 * ���� ������. ��� choice �������� ������� �����������. � ������� ������ setFormat() ����� ���������� �������� ���
 * ������������� ��������������� ������� �������, ������� �������� ��� ������������� ��������� MessageFormat
 * ��� ���������� ����������.
 */
public class FormatedOut_MessageFormat {
    private static final Logger log = Logger.getLogger(FormatedOut_MessageFormat.class);


    public static void main(String[] args) {
        //������: ������������ �������������� ������ ��� �����, � �������� ��������� � �������� ���������� ������ ����������:
        //  ����� �������, ��� ������� (���������������� ������������� ���������),
        // ����� ��� ������� �������������� (������� ������ ����� ������), ����� ���������.


        Object[] param = {new Date(), new Integer(500), new Throwable().getStackTrace()[0], new String("final")};
        String pattern =
                "{0,date,yyyy-MM-dd HH:mm:ss.SSS}" +
                " [{1,choice,300#FINEST|400#FINER|500#FINE|700#CONFIG|800#INFO|900#WARNING|1000#WARNING}]" +
                " at {2}" +
                " Message: {3}";
        MessageFormat mf = new MessageFormat(pattern);
        System.out.println(mf.format(param));
        String patternXML = "<LogRecord>\n" +
                 "  <time>{0,date,yyyy-MM-dd'T'HH:mm:ss.SSS}</time>\n" +
                 "  <level>{1,choice,300#FINEST|400#FINER|500#FINE|700#CONFIG|800#INFO|900#WARNING|1000#WARNING}</level>\n" +
                 "  <stack>{2}</stack>\n" +
                 "  <message>{3}</message>\n" +
                 "</LogRecord>\n";
        mf=new MessageFormat(patternXML);
        System.out.println(mf.format(param));

    }
}
