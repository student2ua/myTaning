package com.tor.callback;

import com.tor.entity.Book;

import javax.persistence.*;

/**
 * User: tor
 * Date: 04.07.14
 * Time: 13:02
 * This class will demonstrates the seperation of callback methods.
 */
public class BookCallbackListener {
    @PrePersist
    public void prePersist(Book book) {
        System.out.println("BookCallbackListener.prePersist:"
                + "Book to be created with book id: " + book.getId());
    }

    @PostPersist
    public void postPersist(Object book) {
        System.out.println("BookCallbackListener.postPersist::"
                + "Book created with book id: " + ((Book) book).getId());
    }

    @PreRemove
    public void preRemove(Book book) {
        System.out.println("BookCallbackListener.preRemove:"
                + " About to delete Book: " + book.getId());
    }

    @PostRemove
    public void postRemove(Book book) {
        System.out.println("BookCallbackListener.postRemove::"
                + " Deleted Book: " + book.getId());
    }

    @PreUpdate
    public void preUpdate(Book book) {
        System.out.println("BookCallbackListener.preUpdate::"
                + " About to update Book: " + book.getId());
    }

    @PostUpdate
    public void postUpdate(Book book) {
        System.out.println("BookCallbackListener.postUpdate::"
                + " Updated Book: " + book.getId());
    }

    @PostLoad
    public void postLoad(Book book) {
        System.out.println("BookCallbackListener.postLoad::"
                + " Loaded Book: " + book.getId());
    }

}
