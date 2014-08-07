package com.tor.util.lockExample;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: tor
 * Date: 04.08.14
 * Time: 18:50
 * обратим внимание на то, что у нас два рода операций — мутаторы (push, pop) и читатель (peek) и
 * используем отдельные режимы блокировки — exclusive и shared
 * ReentrantReadWriteLock тоже произошел от AbstractQueuedSynchronizer и тоже обладает всеми этими fairness,
 * lock, lockInterruptible, tryLock,…
 */
public class StackReentrantReadWriteLock<T> implements Stack<T> {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock rLock = rwLock.readLock();
    private final Lock wLock = rwLock.writeLock();
    private Node<T> top = null;

    @Override
    public T pop() {
        //wLock - EXCLUSIVE mode!
        wLock.lock();
        try {
            Node<T> old = this.top;
            this.top = this.top.next;
            return old.value;
        } finally {
            wLock.unlock();
        }
    }

    @Override
    public void push(T newElement) {
        // wLock - EXCLUSIVE mode!
        wLock.lock();
        try {
            this.top = new Node<T>(newElement, this.top.next);
        } finally {
            wLock.unlock();
        }
    }

    @Override
    public T peek() {
        rLock.lock();
        try {
            return this.top.value;
        } finally {
            rLock.unlock();
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
