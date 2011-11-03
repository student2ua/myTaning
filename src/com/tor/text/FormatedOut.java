package com.tor.text;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.01.2009
 * Time: 16:19:01
 */
public class FormatedOut {
    private static final Logger log = Logger.getLogger(FormatedOut.class);

    public static void main(String[] args) {
        /**
         * ������ ���������� ����� DecimalFormat. DecimalFormat ������������ ��� �������������� ���������� �����
         * (� ������� ��������� ��������� �� � ��������� �� ��������). ������� ��������� DecimalFormat,
         * ��� �������� ����� ������� ������ �� �������� ������������� ����� � DecimalFormatSymbols.
         * � ������� ������������ ��������� �������:
         0 - �����, �� �������� ���� ������������
         # - �����, �� �������� ���� �� ������������
         . - ���������� �����, � ������� ��� ������ ������� ��� �����, ���������� �� ���� ����� ������
         (����� ��� �������) ������������ ��� ��������������
         - - ���� �����
         , - ����������� ����� ��������, �� �� ������� ��� � � ������
         E - ����������� ������� � ���������� � ������� �������
         ����� ������� �� ���������� ������ �������� ��������� ��� ����, ���� ���� ������������� � �������
         ������� ������� ���������� ������ �������, �� ����� ������� ���� ����� � ���������.
         */
        DecimalFormat ooo_xx = new DecimalFormat("000,000.00");
        System.out.println("1225482846.564951");
        System.out.println(ooo_xx.format(1225482846.564951)); //1�225�482�846,56
        System.out.println("ooo_xx = " + ooo_xx.getDecimalFormatSymbols());
        /**
         * ������ ������ �� ������ �������� ���������� ���� � ������ ������� (�������� ������ ����������� �� ����).
         * ��� ���� ����� ����� ������������ setMaximumFractionDigits(), setMinimumFractionDigits() - ��� �������
         * ����� � setMinimumIntegerDigits() � setMaximumIntegerDigits() - ��� ����� �����, ���������� ���� � ������
         * ����� ���������� � ������� setGroupingSize(). 
         * */
        DecimalFormat dg = new DecimalFormat();
        dg.setMinimumIntegerDigits(3);
        dg.setMaximumIntegerDigits(6);

        dg.setMaximumFractionDigits(4);
        dg.setMinimumFractionDigits(2);

        dg.setGroupingSize(3);

        System.out.println("12345678.123456789");
        System.out.println(dg.format(12345678.123456789)); //345�678,1235
        System.out.println("12345678.1");
        System.out.println(dg.format(12345678.1)); //345�678,10
        /**
         * ����� ������ ���������� ������� ����� � ���� ��������� ��� ���� ����� ����� ������������ ������ %,
         * ���� � ������� ����������� ������ %, �� ����� ��������������� ����� ���������� �� 100.
         * */
        DecimalFormat dg2 = new DecimalFormat("0.00%");
        System.out.println("1.23456789 � ���������");
        System.out.println(dg2.format(1.23456789));

        /**
         * �������, ������ ������� ��������� ���� ������������ ������ ������� \u2030.
         ������ ���������������� ������, ��� ����� �������� ������. ��� ���� ����� ������ ������ \u00A4, �������������� �
         ������������ ����� �� ������� ������������ ����������� ������ (�������� ���. ��� $),
         � ���� ��� ������� \u00A4\u00A4 �� ����� �������� ������������� ����������� ������ (RUB � USD �������������).
         ���� � ������� ������������ ������ \u00A4, �� ������ ���������� ����� ���������� �� �������� �����������
         (� ����������� ����� �� ��������� � ���������� ������). ������ ������ ���������� ��������� ������� ���
         ������������� � ������������� �������, ��� ����� ������������ ������ ;(����� � �������), ��� ��� ���� �� ���� ��������� ��������
         ��� ������������� �����, � ��� ��� ����� - ��� �������������. */

        //������: ������� ����� � ��������� �� ���� ������ ����� ������� � �������� ������,
        //  ��� ������������� ����� ����� ������ ���� ��������� (+), � ��� ������������� (-):
        dg2 = new DecimalFormat("+000.00 \u00A4\u00A4;����� ���� -000.00 \u00A4\u00A4");
        //dg2=new DecimalFormat("'(+)'0.00\u00A4;'(-)'0.00\u00A4");
        System.out.println(dg2.format(12345.6789));
        System.out.println(dg2.format(-12345.6789));

        System.out.println("������: ��������������� ����� � ��������� 2 ����� ����� ������� � ������� ��� ����:");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.JAPAN);
        dg2 = new DecimalFormat("000.00 \u00A4\u00A4", symbols);
        System.out.println(dg2.format(-12345.6789));

        System.out.println("������: ��������������� ����� � �������� ������� �������� � ���:");
        dg2 = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);
        System.out.println(dg2.format(-12345.6789));

        System.out.println("������: ��������� ����� � �������� �������:");
        symbols = new DecimalFormatSymbols(Locale.US);
        dg2 = new DecimalFormat("0.0 \u00A4", symbols);
        try {
            System.out.println(dg2.parse("12345.6789 $"));
        } catch (ParseException e) {
            //e.printStackTrace();
            log.error(e);
        }
        
    }
}
