package obuchenie.javaee.util;

import obuchenie.javaee.exceptions.IteratorException;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 20:26:06
 * To change this template use File | Settings | File Templates.
 */
public class ValueListHandler implements ValueListIterator {
    private static final Logger log = Logger.getLogger(ValueListHandler.class);
    protected List list;
    protected ListIterator listIterator;

    public ValueListHandler() {
    }

    protected void setList(List list)
            throws IteratorException {
        this.list = list;
        if (list != null)
            listIterator = list.listIterator();
        else
            throw new IteratorException("List empty");
    }

    public Collection getList() {
        return list;
    }

    public int getSize() throws IteratorException {
        int size = 0;

        if (list != null)
            size = list.size();
        else
            throw new IteratorException(...); // No Data

        return size;
    }

    public Object getCurrentElement()
            throws IteratorException {

        Object obj = null;
        // Не будет продвигать итератор
        if (list != null) {
            int currIndex = listIterator.nextIndex();
            obj = list.get(currIndex);
        } else
            throw new IteratorException(...);
        return obj;

    }

    public List getPreviousElements(int count) throws IteratorException {
        int i = 0;
        Object object = null;
        LinkedList list = new LinkedList();
        if (listIterator != null) {
            while (listIterator.hasPrevious() && (i < count)) {
                object = listIterator.previous();
                list.add(object);
                i++;
            }
        }// конец if
        else
            throw new IteratorException("No PreviousElements"); // Нет данных

        return list;
    }

    public List getNextElements(int count) throws IteratorException {
        int i = 0;
        Object object = null;
        LinkedList list = new LinkedList();
        if (listIterator != null) {
            while (listIterator.hasNext() && (i < count)) {
                object = listIterator.next();
                list.add(object);
                i++;
            }
        } // конец if
        else
            throw new IteratorException("No Next Elements"); // Нет данных

        return list;
    }

    public void resetIndex() throws IteratorException {
        if (listIterator != null) {
            listIterator = list.listIterator();
        } else
            throw new IteratorException("No resetIndex, listIterator=null"); // No data
    }
}
