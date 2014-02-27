package com.tor.text;

import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.01.2009
 * Time: 18:33:54
 * MessageFormat предназначен для формирования "читабельных" сообщений в программах. При создании MessageFormat в
 * конструкторе указывается сообщение в котором содержатся символы подстановки
 * (работает аналогично PrintStream.printf()). Подстановочные символы заключаются в фигурные скобки. При форматировании
 * MessageFormat принимает в качестве аргументов массив объектов и подставляет их в нужное место. Если для переданного
 * объекта нет соответсвующего подстановочного символа, то он игнорируется, и наоборот если для подстановочного символа
 * не передан объект то оставляется как есть. Для преобразования объекта в строку использется или метод toString() или
 * один из описанных ранее форматеров.
 * <p/>
 * Формат подстановочного символа следующий: {0[,<тип_формата>[,<параметры_формата>]]}, в квадратных скобках указанны
 * необязательные параметры. <тип_формата> - название форматтера используемого для форматирования, принимает следующие
 * значения: number, date, time, choice. <параметры_формата> - параметры форматирования специфичные для каждого
 * форматтера, для даты и времени можно указать либо один из стандартных шаблонов (short, medium, long, full) либо
 * непосредственно шаблон даты), аналогично для числа либо стандартный шаблон (integer, currency, percent) либо
 * свой шаблон. Для choice указание шаблона обязательно. С помощью метода setFormat() можно установить форматер для
 * определенного подстановочного символа вручную, полезно например для использования вложенных MessageFormat
 * или собсвенных форматеров.
 */
public class FormatedOut_MessageFormat {
    private static final Logger log = Logger.getLogger(FormatedOut_MessageFormat.class);


    public static void main(String[] args) {
        //Задача: организовать форматирование данных для логов, в качестве параметра в форматер передается массив содержащий:
        //  время события, тип события (предопределенная целочисленная константа),
        // место где событие зафиксированно (верхняя строка стека вызова), текст сообщения.


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
        mf = new MessageFormat(patternXML);
        System.out.println(mf.format(param));

    }
}
