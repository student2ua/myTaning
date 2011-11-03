package com.tor.text;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.01.2009
 * Time: 17:57:26
 * ��� �������������� ��� ������������ ����� SimpleDateFormat, ��� ������� ��������� ���� ���� �������� � �������
 * java.util.Calendar. ��� � DecimalFormat �� ������� �� ���������� �������, � ������� ����������� ������� ������������
 * �� ��� ���� ���� ������ Calendar, ������ ���� ������������ � Calendar ����� ���� ����������� � �������. ��� �������
 * ����������������� � ��� ��������� ����� ����������������, ������� ���� �� ������ �������� ����� ������ ����� � ������
 * ����� �� ����� � ���������. ���� ������ ���������� �����:
 * <b>��������</b> - ��������� ���� �������� �����, ���� ������ ����, �� �� �������� ���� �� ���������,
 * ���� ����� �� ��������� (������� ������� ����� ������� ��� ���� ������ �����������), ���������� ���������� ���� ����,
 * ���� ������ y ����������� ������ 3-� ���, �� ��������� 2 ����� ����� ��������� ������� ��������� ����
 * <b>����������</b> - ��������� ���� �������� �����, ��� ��������� ����� ����������� ����������� ������
 * (��� ������ � ��������� ����), ��� ������ ��� (���, ����� ����� � ����������� ��������� ����), ����� ����� �����������
 * � ������ ������ ������ �������������� ������ �� ���������� �������� (4 � ����� ������ �����, ����� �����������)
 * <b>���������</b> - ��������� ���� ����� ���� ��� ������ ��� � �������, ����� ���� ����� ���� ��� �����, ���� ������ M
 * ����������� 2 ��� 1 ���, �� ��� ����� � ����������� � ��������� �� ������� ������. ���� 3 ����, �� ��� �����������,
 * ������������� �������� ������. 4 � ����� ������ �������� ������.
 * ���������� �������� ���� (������ ������ ����� ����� ����� � ������������):
 * d - ���� ������, M - �����, y - ���, H - ���� (24-� ������� �����), m - ������, s - �������.
 */
public class FormatedOut_SimpleDateFormat {
    private static final Logger log = Logger.getLogger(FormatedOut_SimpleDateFormat.class);

    public static void main(String[] args) {

        System.out.println("������: ������� ������� ���� � ���� ����� �������� ������ ������ ���:");
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        System.out.println("dd MMMM yyyy = " + formatter.format(new Date(System.currentTimeMillis())));

        /**��������� ������������ ��� �������������� ����� ���������� � ������� setCalendar(),
         * �������� � ������� getCalendar(). */
        System.out.println("������: �������� ������� ���� �� ����������� ���������:");
        formatter.setCalendar(new sun.util.BuddhistCalendar());
        System.out.println("Buddhist = " + formatter.format(new Date(System.currentTimeMillis())));

        Locale ru = new Locale("ru");
        DateFormat sdf = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, ru);
        System.out.println("FULL " +sdf.format(new Date()));
        sdf = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, ru);
        System.out.println("LONG "+sdf.format(new Date()));
        sdf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, ru);
        System.out.println("MEDIUM "+sdf.format(new Date()));
        sdf = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, ru);
        System.out.println("SHORT "+sdf.format(new Date()));

        System.out.println("������: �������� �������� ������� ��� ������� ������ " +
                "� ���������� ���������� (�� 29 ��������, � 29 ��������):");

        String[] months = {"������", "�������", "�����", "������", "���", "����", "����", "�������", "��������", "�������", "������", "�������" };
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("ru"));
        symbols.setMonths(months);
        sdf = new SimpleDateFormat("dd MMMM yyyy �.", symbols);
        System.out.println(sdf.format(new Date()));

        System.out.println("������: ��������� � ���� ����� ������������ ����� � ������ bakup_20050929_1836.rar");
        formatter=new SimpleDateFormat("'bakup_'yyyyMMdd_HHmm'.rar'"); //�����������!!! ��� ������� '
        try {
            System.out.println("bakup_20050929_1836.rar = "+formatter.parse("bakup_20050929_1836.rar"));
        } catch (ParseException e) {
            log.error(e);
        }
    }
}
