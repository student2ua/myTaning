package com.tor.util.lockExample;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * User: tor
 * Date: 04.08.14
 * Time: 17:52
 * Семафор, «заряженный единицей», работает как обычная блокировка и называется Binary Semaphore.
 */
public class StackBinarySemaphore<T> implements Stack<T> {
    private final Semaphore sem;
    private Node<T> top = null;

    public StackBinarySemaphore(boolean fair) {
        this.sem = new Semaphore(1, fair);
    }

    @Override
    public T pop() {
        sem.acquireUninterruptibly();
        try {
            Node<T> old = this.top;
            this.top = this.top.next;
            return old.value;
        } finally {
            sem.release();
        }
    }

    @Override
    public void push(T newElement) {
        sem.acquireUninterruptibly();
        try {
            this.top = new Node<T>(newElement, this.top);
        } finally {
            sem.release();
        }
    }

    public void pushInterruptibly(T newElement) throws InterruptedException {
        sem.acquire();
        try {
            this.top = new Node<T>(newElement, this.top);
        } finally {
            sem.release();
        }
    }

    public boolean pushTry(T newElement) throws InterruptedException {
        if (sem.tryAcquire()) {
            try {
                this.top = new Node<T>(newElement, this.top);
                return true;
            } finally {
                sem.release();
            }
        } else return false;
    }

    public boolean pushTry(T newElement, long time, TimeUnit unit) throws InterruptedException {
        if (sem.tryAcquire(time, unit)) {
            try {
                this.top = new Node<T>(newElement, this.top);
                return true;
            } finally {
                sem.release();
            }
        } else return false;
    }

    @Override
    public T peek() {
        sem.acquireUninterruptibly();
        try {
            return top.value;
        } finally {
            sem.release();
        }
    }


    private static class Node<E> {
        private final E value;
        private final Node<E> next;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }
}
