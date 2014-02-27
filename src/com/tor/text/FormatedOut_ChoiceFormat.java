package com.tor.text;

import org.apache.log4j.Logger;

import java.text.ChoiceFormat;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.01.2009
 * Time: 17:38:24
 * ChoiceFormat. Этот класс осуществляет форматирование следующим образом: в качестве аргумента
 * конструктор принимает два массива,
 * первый массив чисел {X1, X2, ... Xn},
 * второй массив строк {S1, S2, ... Sn},
 * при форматировании числа определяется к какому диапазону оно принадлежит
 * ( x < X1 или Xi <= x < Xi+1 или Xn <= x) и выдатеся соответствующая ему строка Si.
 * Числа в массиве должны идти строго по возрастанию.
 */
public class FormatedOut_ChoiceFormat {
    private static final Logger log = Logger.getLogger(FormatedOut_ChoiceFormat.class);

    public static void main(String[] args) {
        System.out.println("Задача: по номеру дня недели получить его название:");
        double[] days = {1, 2, 3, 4, 5, 6, 7};
        String[] nameDays = {"Пн", "Вт", "Ср", "Чт", "Пн", "Сб", "Вс"};
        ChoiceFormat format = new ChoiceFormat(days, nameDays);
        System.out.println("days[0] = " + format.format(days[0]));
        /**
         * Иногда бывает необходимо выделить случаи X=A, X<A, X>A, т.е. неравенства строгие,
         * для этих целей в ChoiceFormat есть методы previousDouble() и nextDouble().
         * Они возвращают ближайшее к X слева и справа соответсвенно.
         */
        System.out.println("Задача: реализовать аналог функции SIGN:");
        double[] limits = {ChoiceFormat.previousDouble(0.0), 0.0, ChoiceFormat.nextDouble(0.0)};
        String[] formats = {"negative", "zero", "positive",};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        System.out.println(cf.format(0.00000000000000000000001));
        System.out.println(cf.format(0.0));
        System.out.println(cf.format(-0.00000000000000000000001));
        /**
         * Существует другой способ задания шаблонов для ChoiceFormat, это задать один текстовый шаблон в котором
         * описать все границы диапазонов и значения для этих диапазонов в формате:
         * <число>[#|<]<значение_для данного_диапазона>[|<следующий_шаблон>].
         *  Формировать его вручную не очень удобно, проще получит автоматом из готовго форматера. */
        System.out.println("Задача: получить шаблон для функции SIGN:");
        System.out.println(cf.toPattern());

        /**При парсинге ChoiceFormat определяет к какому диапазону принадлежит указанная строка и выдает число из
         * этого диапазона (как правило левая граница диапазона).
         */
        System.out.println("Задача: получить номер дня недели по его имени:");
        try {
            System.out.println("Вт " +
                    "" + format.parse("Вт"));
        } catch (ParseException e) {
//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            log.error(e);
        }
    }
}
