package com.tor.text;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 15.01.2009
 * Time: 17:57:26
 * Для форматирования дат предназначен класс SimpleDateFormat, для полного понимания надо быть знакомым с классом
 * java.util.Calendar. Как и DecimalFormat он основан на символьном шаблоне, в шаблоне применяются символы обозначающие
 * те или иные поля класса Calendar, каждое поле определенное в Calendar имеет свое отображение в шаблоне. Все символы
 * регистрозависимые и все латинские буквы зарезервированны, поэтому если вы хотите вставить какую нибудь букву в шаблон
 * лучше ее брать в апострофы. Поля бывают нескольких видов:
 * <b>числовые</b> - значением поля является число, если символ один, то не значищие нули не выводятся,
 * если более то выводятся (столько сколько нулей сколько раз этот символ встречается), исключение составляет поле года,
 * если символ y встречается меньше 3-х раз, то выводятся 2 цифры иначе действуют правила описанные выше
 * <b>символьные</b> - значением поля является текст, для некоторых полей допускается сокращенная запись
 * (дни недели и временные зоны), для других нет (ера, время суток и стандартная временная зона), выбор межде сокращенной
 * и полной формой записи осуществляется исходя из количества символов (4 и более полная форма, менее сокращенная)
 * <b>смешанные</b> - значением поля может быть как числом так и текстом, такое поле всего одно это месяц, если символ M
 * встречается 2 или 1 раз, то это число в соответсвии с правилами из первого пункта. Если 3 раза, то это сокращенное,
 * трехбуквенное название месяца. 4 и более полное название месяца.
 * Перечислим основные поля (полный список полей можно найти в документации):
 * d - день месяца, M - месяц, y - год, H - часы (24-х часовая шкала), m - минуты, s - секунды.
 */
public class FormatedOut_SimpleDateFormat {
    private static final Logger log = Logger.getLogger(FormatedOut_SimpleDateFormat.class);

    public static void main(String[] args) {

        System.out.println("Задача: вывести текущую дату в виде число название месяца полный год:");
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        System.out.println("dd MMMM yyyy = " + formatter.format(new Date(System.currentTimeMillis())));

        /**Календарь используемый для форматирования можно установить с помощью setCalendar(),
         * получить с помощью getCalendar(). */
        System.out.println("Задача: получить текущую дату по буддийскому календарю:");
        formatter.setCalendar(new sun.util.BuddhistCalendar());
        System.out.println("Buddhist = " + formatter.format(new Date(System.currentTimeMillis())));

        Locale ru = new Locale("ru");
        DateFormat sdf = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, ru);
        System.out.println("FULL " + sdf.format(new Date()));
        sdf = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, ru);
        System.out.println("LONG " + sdf.format(new Date()));
        sdf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, ru);
        System.out.println("MEDIUM " + sdf.format(new Date()));
        sdf = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, ru);
        System.out.println("SHORT " + sdf.format(new Date()));

        System.out.println("Задача: получить названия месяцев для русской локали " +
                "в правильной словоформе (не 29 Сентябрь, а 29 сентября):");

        String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("ru"));
        symbols.setMonths(months);
        sdf = new SimpleDateFormat("dd MMMM yyyy г.", symbols);
        System.out.println(sdf.format(new Date()));

        System.out.println("Задача: перевести в дату время формирования файла с именем bakup_20050929_1836.rar");
        formatter = new SimpleDateFormat("'bakup_'yyyyMMdd_HHmm'.rar'"); //Внимательно!!! доп символы '
        try {
            System.out.println("bakup_20050929_1836.rar = " + formatter.parse("bakup_20050929_1836.rar"));
        } catch (ParseException e) {
            log.error(e);
        }
    }
}
