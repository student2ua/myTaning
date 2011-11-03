package obuchenie.log4j_additivity;

import obuchenie.log4j_additivity.pckg1.FirstClass;
import obuchenie.log4j_additivity.pckg1.sub.FirstSubClass;
import obuchenie.log4j_additivity.pckg2.SecondClass;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 20.01.2011
 * Time: 13:12:27
 * Additivity Flag применяется в следующих случаях:
 * 1) Когда необходимо перенаправить вывод протоколов из определённого пакета (и всех его подпакетов) в
 * отдельный Appender (logfile), не выводя протокол в другие Appenders!
 * 2) Полностью отключить протоколирование для определённого пакета (не указывать Appender).
 * При этом Log4j будет генерировать «log4j:WARN Please initialize the log4j system properly.» предупреждения.
 * Генерацию предупреждений можно отключить, установив «org.apache.log4j.helpers.LogLog.setQuietMode(true);» (применять только в исключительных случаях!).
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(final String[] args) {
        logger.debug("main");
        new FirstClass();
        new SecondClass();
        new FirstSubClass();
    }
}
/**
 *  Первая конфигурация: log4j_f
 Минимальная Log4j конфигурация, только «default» ROOT Logger, с консольным Appender «A1?.
 -- обычный вывод
 * Вторая конфигурация:   log4j_s
 Дополнительно к «default» Logger определим два логгера:
 1) foo.additivity Logger (default additivity = true)
 2) foo.additivity.pckg1 Logger (additivity = true и дополнительный Appender «A2?)
 --- Видно, что протокол из пакета foo.additivity.pckg1 выводится в оба логгера «A1? и «A2?

 * Третья конфигурация: log4j_t
 Установим для логгера «foo.additivity.pckg1?, Additivitiy Flag = «FALSE»
 --Установка флага Additivity на «FALSE» приводит к тому, что все Appenders из логгера высшей иерархии не добавляются
 в текущий логгер. В нашем случае к логгеру «foo.additivity.pckg1? не добавился Appender «A1? из «default» ROOT логгера.
 */
