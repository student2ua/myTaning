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
         * Первым рассмотрим класс DecimalFormat. DecimalFormat предназначен для форматирования десятичных чисел
         * (с другими системами счисления он к сожалению не работает). Создаем экземпляр DecimalFormat,
         * при создании можно указать шаблон по которому форматировать числа и DecimalFormatSymbols.
         * В шаблоне используются следующие символы:
         0 - цифра, не значащие нули показываются
         # - цифра, не значащие нули не показываются
         . - десятичная точка, в шаблоне она всегда пишется как точка, независимо от того какой символ
         (точка или запятая) используется для форматирования
         - - знак минус
         , - разделитель групп разрядов, те же правила что и с точкой
         E - разделитель мантисы и экспоненты в научной нотации
         Любые символы не являющиеся частью шаблонов выводятся как есть, если есть необходимость в шаблоне
         вывести символы являющиеся частью шаблона, то такие символы надо взять в апострофы.
         */
        DecimalFormat ooo_xx = new DecimalFormat("000,000.00");
        System.out.println("1225482846.564951");
        System.out.println(ooo_xx.format(1225482846.564951)); //1 225 482 846,56
        System.out.println("ooo_xx = " + ooo_xx.getDecimalFormatSymbols());
        /**
         * Иногда бывает не удобно задавать количество цифр с помщью шаблона (например шаблон формируется не нами).
         * Для этих целей можно использовать setMaximumFractionDigits(), setMinimumFractionDigits() - для дробной
         * части и setMinimumIntegerDigits() и setMaximumIntegerDigits() - для целой части, количество цифр в группе
         * можно установить с помощью setGroupingSize(). 
         * */
        DecimalFormat dg = new DecimalFormat();
        dg.setMinimumIntegerDigits(3);
        dg.setMaximumIntegerDigits(6);

        dg.setMaximumFractionDigits(4);
        dg.setMinimumFractionDigits(2);

        dg.setGroupingSize(3);

        System.out.println("12345678.123456789");
        System.out.println(dg.format(12345678.123456789)); //345 678,1235
        System.out.println("12345678.1");
        System.out.println(dg.format(12345678.1)); //345 678,10
        /**
         * Часто бывает необходимо вывести число в виде процентов для этих целей можно использовать символ %,
         * если в шаблоне встречается символ %, то перед форматированием число умножается на 100.
         * */
        DecimalFormat dg2 = new DecimalFormat("0.00%");
        System.out.println("1.23456789 в процентах");
        System.out.println(dg2.format(1.23456789));

        /**
         * промиле, вместо символа процентов надо использовать символ промиле \u2030.
         Другая распространенная задача, это вывод денежных единиц. Для этих целей служит символ \u00A4, использованный в
         единственном числе он выводит национальное обозначение валюты (например руб. или $),
         а если его удвоить \u00A4\u00A4 то быдет выведено международное обозначение валюты (RUB и USD соответсвенно).
         Если в шаблоне присутствует символ \u00A4, то символ десятичной точки заменяется на денежный разделитель
         (в большинстве стран он совпадает с десятичной точкой). Иногда бывает необходимо разделить шаблоны для
         положительных и отрицательных величин, для этого используется символ ;(точка с запятой), все что идет до него считается шаблоном
         для положительных чисел, а все что после - для отрицательных. */

        //Задача: вывести число с точностью до двух знаков после запятой и символом валюты,
        //  для положительных чисел перед числом надо поставить (+), а для отрицательных (-):
        dg2 = new DecimalFormat("+000.00 \u00A4\u00A4;плохи дела -000.00 \u00A4\u00A4");
        //dg2=new DecimalFormat("'(+)'0.00\u00A4;'(-)'0.00\u00A4");
        System.out.println(dg2.format(12345.6789));
        System.out.println(dg2.format(-12345.6789));

        System.out.println("Задача: отформатировать число с точностью 2 знака после запятой и вывести как йены:");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.JAPAN);
        dg2 = new DecimalFormat("000.00 \u00A4\u00A4", symbols);
        System.out.println(dg2.format(-12345.6789));

        System.out.println("Задача: отформатировать число в денежном формате принятом в США:");
        dg2 = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);
        System.out.println(dg2.format(-12345.6789));

        System.out.println("Задача: разобрать число в денежном формате:");
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
