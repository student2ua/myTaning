package com.tor.stateful;

import com.tor.interceptor.BusinessInterceptor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 12:38
 * To change this template use File | Settings | File Templates.
 */
@Interceptors({BusinessInterceptor.class})
@Stateful/*(name = "LibraryStatefulSessionEJB")*/
public class LibraryStatefulSessionBean implements LibraryStatefulSessionBeanRemote {

    List<String> bookShelf;

    public LibraryStatefulSessionBean() {
        bookShelf = new ArrayList<String>();
    }

    public void addBook(String book) {
        bookShelf.add(book);
    }

    public List<String> getBooks() {
        return bookShelf;
    }

    @PostConstruct
    void postConstruct() {
        System.out.println("LibraryStatefulSessionBean.postConstruct:: bean created.");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("LibraryStatefulSessionBean.preDestroy: bean removed.");
    }

    @PrePassivate
    void prePassivate() {
        System.out.println("LibraryStatefulSessionBean.prePassivate: bean passivated.");
    }

    @PostActivate
    void postActivate() {
        System.out.println("LibraryStatefulSessionBean.postActivate: bean activated.");
    }
}
