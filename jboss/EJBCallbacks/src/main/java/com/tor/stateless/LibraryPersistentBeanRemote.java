package com.tor.stateless;

import com.tor.entity.Book;

import javax.ejb.Remote;
import java.util.List;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
@Remote
public interface LibraryPersistentBeanRemote {
    void addBook(Book book);

    List<Book> getBooks();
}
