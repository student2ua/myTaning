package com.tor.text;

import org.apache.log4j.Logger;

import java.text.ChoiceFormat;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.01.2009
 * Time: 17:38:24
 * ChoiceFormat. ���� ����� ������������ �������������� ��������� �������: � �������� ���������
 * ����������� ��������� ��� �������,
 * ������ ������ ����� {X1, X2, ... Xn},
 * ������ ������ ����� {S1, S2, ... Sn},
 * ��� �������������� ����� ������������ � ������ ��������� ��� �����������
 * ( x < X1 ��� Xi <= x < Xi+1 ��� Xn <= x) � �������� ��������������� ��� ������ Si.
 * ����� � ������� ������ ���� ������ �� �����������.
 */
public class FormatedOut_ChoiceFormat {
    private static final Logger log = Logger.getLogger(FormatedOut_ChoiceFormat.class);

    public static void main(String[] args) {
        System.out.println("������: �� ������ ��� ������ �������� ��� ��������:");
        double[] days = {1, 2, 3, 4, 5, 6, 7};
        String[] nameDays = {"��", "��", "��", "��", "��", "��", "��" };
        ChoiceFormat format = new ChoiceFormat(days, nameDays);
        System.out.println("days[0] = " + format.format(days[0]));
        /**
         * ������ ������ ���������� �������� ������ X=A, X<A, X>A, �.�. ����������� �������,
         * ��� ���� ����� � ChoiceFormat ���� ������ previousDouble() � nextDouble().
         * ��� ���������� ��������� � X ����� � ������ �������������.
         */
        System.out.println("������: ����������� ������ ������� SIGN:");
        double[] limits = {ChoiceFormat.previousDouble(0.0), 0.0, ChoiceFormat.nextDouble(0.0)};
        String[] formats = {"negative", "zero", "positive",};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        System.out.println(cf.format(0.00000000000000000000001));
        System.out.println(cf.format(0.0));
        System.out.println(cf.format(-0.00000000000000000000001));
        /**
         * ���������� ������ ������ ������� �������� ��� ChoiceFormat, ��� ������ ���� ��������� ������ � �������
         * ������� ��� ������� ���������� � �������� ��� ���� ���������� � �������:
         * <�����>[#|<]<��������_��� �������_���������>[|<���������_������>].
         *  ����������� ��� ������� �� ����� ������, ����� ������� ��������� �� ������� ���������. */
        System.out.println("������: �������� ������ ��� ������� SIGN:");
        System.out.println(cf.toPattern());

        /**��� �������� ChoiceFormat ���������� � ������ ��������� ����������� ��������� ������ � ������ ����� ��
         * ����� ��������� (��� ������� ����� ������� ���������).
         */
        System.out.println("������: �������� ����� ��� ������ �� ��� �����:");
        try {
            System.out.println("�� " +
                    ""+format.parse("��"));
        } catch (ParseException e) {
//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            log.error(e);
        }
    }
}
