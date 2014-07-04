package com.tor.stateless;

import com.tor.entity.Book;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 12:52
 */
@Stateless/*(name = "LibraryPersistentEJB")*/
public class LibraryPersistentBean implements LibraryPersistentBeanRemote {
    public LibraryPersistentBean() {
    }

    @PersistenceContext(unitName = "EntityEjbPU")
    private EntityManager manager;

    @Override
    public void addBook(Book book) {
        manager.persist(book);
    }

    @Override
    public List<Book> getBooks() {
        return manager.createQuery("From Book").getResultList();
    }

    @PostConstruct
    void postConstruct() {
        System.out.println("postConstruct:: LibraryPersistentBean session bean created with entity Manager object: ");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("preDestroy: LibraryPersistentBean session bean is removed ");
    }
}
