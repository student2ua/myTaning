package obuchenie;


/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 06.08.2008
 * Time: 19:47:09
 * To change this template use File | Settings | File Templates.
 */
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

/**
* Этот класс предназначен для сортировки списка файлов
*
* @author Стаценко Владимир
* http://www.vova-prog.narod.ru
*/
public class FileSorter implements Comparator {

    //класс для работы с регулярными выражениями
    Pattern p = null;
    //класс для работы со строками на разных языках
    Collator collator = null;

    /** Создает новые экземпляры FileSorter */
    public FileSorter() {
        //определяем системный символ разделитель и создаем
        //шаблон на его основе
        String separator = File.separator;
        if(separator.equals("\\")) {
            separator = "\\";
        }
        //создаем шаблон на основе символа-разделителя
        p = Pattern.compile(separator,
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        //получаем системные настройки (язык и страну)
        String country = System.getProperty("user.country");
        String language = System.getProperty("user.language");
        //создаем экземпляр класса для сравнения строк на
        //основе региональных настроек
        collator = Collator.getInstance(new Locale(country, language));
    }

    /**
     * Этот метод выполняет сравнение имен двух файлов.
     * Возвращает:
     *     1 если первый параметр (о1) больше второго (о2),
     *    -1 если первый параметр (о1) меньше второго (о2),
     *     0 если они равны.
     * Имя первого файла считается больше второго имени, если
     * первый файл находится ближе к корню дерева папок.
     * Если файлы находятся в одной папке, то больше то имя,
     * которое идет первым по алфавиту.
     * @param o1 объект типа File
     * @param o2 объект типа File
     * @return результат сравнения
     */
    public int compare(Object o1, Object o2) {
        //если объекты не равны null и имеют тип File
        if(o1 != null && o2 != null &&
                o1 instanceof File && o2 instanceof File) {
            //приводим к типу File
            File f1 = (File)o1;
            File f2 = (File)o2;
            //получаем полный путь к имени файла
            String fullPath1 = f1.getAbsolutePath();
            String fullPath2 = f2.getAbsolutePath();
            //проверяем равенство имен
            if(fullPath1.equals(fullPath2)) {
                //возвращаем 0, т.к. имена одинаковы
                return 0;
            }
            //определяем глубину размещения файла в дереве папок
            //для этого разбиваем полный путь к файлу на
            //лексемы, и определяем их количество
            String[] res1 = p.split(fullPath1);
            String[] res2 = p.split(fullPath2);
            if(res1.length > res2.length) {
                //возвращаем 1, если глубина вложения первого
                //файла больше глубины вложения второго
                return 1;
            }
            if(res1.length < res2.length) {
                //возвращаем "-1" в противном случае
                return -1;
            }
            if(res1.length == res2.length) {
                //если файлы находятся на одинаковой глубине,
                //сортируем их в соответствии с алфавитом
                return collator.compare(fullPath1, fullPath2);
            }
        }
        //здесь мы возвращаем 0, т.к. сравнение объектов
        //выполнить невозможно (т.е. считаем, что объекты
        //одинаковые, во всяком случае, сортировать их
        //нет смысла)
        return 0;
    }

    /**
     * Этот метод выполняет сортировку списка файлов
     * @param fileList не отсортированный список файлов
     * @return отсортированный список файлов
     */
    public List sort(List fileList) {
        //создаем список для результатов (такого же размера
        //как и исходный список)
        ArrayList res = new ArrayList(fileList.size());
        //копируем список
        res.addAll(fileList);
        //выполняем сортировку
        Collections.sort(res, this);
        //возвращаем результат
        return res;
    }
}