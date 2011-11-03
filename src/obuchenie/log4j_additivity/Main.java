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
 * Additivity Flag ����������� � ��������� �������:
 * 1) ����� ���������� ������������� ����� ���������� �� ������������ ������ (� ���� ��� ����������) �
 * ��������� Appender (logfile), �� ������ �������� � ������ Appenders!
 * 2) ��������� ��������� ���������������� ��� ������������ ������ (�� ��������� Appender).
 * ��� ���� Log4j ����� ������������ �log4j:WARN Please initialize the log4j system properly.� ��������������.
 * ��������� �������������� ����� ���������, ��������� �org.apache.log4j.helpers.LogLog.setQuietMode(true);� (��������� ������ � �������������� �������!).
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
 *  ������ ������������: log4j_f
 ����������� Log4j ������������, ������ �default� ROOT Logger, � ���������� Appender �A1?.
 -- ������� �����
 * ������ ������������:   log4j_s
 ������������� � �default� Logger ��������� ��� �������:
 1) foo.additivity Logger (default additivity = true)
 2) foo.additivity.pckg1 Logger (additivity = true � �������������� Appender �A2?)
 --- �����, ��� �������� �� ������ foo.additivity.pckg1 ��������� � ��� ������� �A1? � �A2?

 * ������ ������������: log4j_t
 ��������� ��� ������� �foo.additivity.pckg1?, Additivitiy Flag = �FALSE�
 --��������� ����� Additivity �� �FALSE� �������� � ����, ��� ��� Appenders �� ������� ������ �������� �� �����������
 � ������� ������. � ����� ������ � ������� �foo.additivity.pckg1? �� ��������� Appender �A1? �� �default� ROOT �������.
 */
