package com.tor.util.lockExample;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: tor
 * Date: 04.08.14
 * Time: 17:24
 * Вопрос, а зачем же использовать ReentrantLock, а не встроенный монитор/synchronized?
 * Ну, во-первых, обратите свой взор на его богатое API (честность/fairness, lock, lockInterruptibly, tryLock, ...)
 * А, во-вторых, обратите свой взор на главы «13.1. Lock and ReentrantLock», «13.2. Performance Considerations» и
 * «13.4. Choosing Between Synchronized and ReentrantLock» книги Brian Goetz и других «Java Concurrency in Practice»,
 * где проводится сравнение synchronized и ReentrantLock.
 *
 * В этом примере расписан доп только метод push(). peek() и pop() пропустили
 */
public class StackReentrantLock<T> implements Stack<T> {
    private final Lock lock = new ReentrantLock();
    private Node<T> top = null;

    @Override
    public T pop() {
        lock.lock();
        try {
            Node<T> old = this.top;
            this.top = this.top.next;
            return old.value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void push(T newElement) {
        lock.lock();
        try {//Thread.stop()
            this.top = new Node<T>(newElement, this.top);
        } finally {
            lock.unlock();
        }
    }

    /**
     * push, с возможностью прервать ожидание захвата блокировки
     * посредством Thread.interrupt() -> InterruptedException
     */
    public void pushInterruptibly(T newElement) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            this.top = new Node<T>(newElement, this.top);
        } finally {
            lock.unlock();
        }
    }

    /**
     * push, с возможностью захвата блокировки только
     * в том случае, если она свободна
     */
    public boolean pushTry(T newElement) throws InterruptedException {
        if (lock.tryLock()) {
            try {
                this.top = new Node<T>(newElement, this.top);
                return true;
            } finally {
                lock.unlock();
            }
        } else {
            return false;
        }
    }

    /**
     * push, с возможностью захвата блокировки
     * с ограниченным временем ожидания
     */
    public boolean pushTry(T newElement, long time, TimeUnit unit) throws InterruptedException {
        if (lock.tryLock(time, unit)) {
            try {
                this.top = new Node<T>(newElement, this.top);
                return true;
            } finally {
                lock.unlock();
            }
        } else {
            return false;
        }
    }

    @Override
    public T peek() {
        lock.lock();
        try {
            return top.value;
        } finally {
            lock.unlock();
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
