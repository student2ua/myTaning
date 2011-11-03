package obuchenie.javaee.util;

import obuchenie.javaee.exceptions.IteratorException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.04.2010
 * Time: 20:28:34
 * To change this template use File | Settings | File Templates.
 */
public interface ValueListIterator {
    public int getSize() throws IteratorException;

    public Object getCurrentElement() throws IteratorException;

    public List getPreviousElements(int count) throws IteratorException;

    public List getNextElements(int count) throws IteratorException;

    public void resetIndex() throws IteratorException;
}
