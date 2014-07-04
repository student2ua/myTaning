package com.tor.stateful;

import javax.ejb.Remote;
import java.util.List;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
@Remote
public interface LibraryStatefulSessionBeanRemote {
    void addBook(String book);
    List<String> getBooks();
}
