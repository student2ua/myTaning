package com.tor.util.lockExample;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * User: tor
 * Date: 07.08.14
 * Time: 13:46
 * Тут мы, в случае занятого стека, не передаем управление JVM/OS (в отличии от synchronized, ReentrantLock,
 * Semaphore, ReadWriteReentrantLock, ...)
 * Протокол захвата — перевод AtomicBoolean: false -> true (при конкуренции со стороны других потоков).
 Протокол освобождения — перевод AtomicBoolean: true -> false (без конкуренции со стороны других потоков).
 */
public class StackSpinLockAtomic<T> implements Stack<T> {
    private final AtomicBoolean locked = new AtomicBoolean();
    private Node<T> top = null;

    @Override
    public T pop() {
        while (!locked.compareAndSet(false, true)) {
            //NOP
        }
        Node<T> old;
        try {
            old = this.top;
            this.top = this.top.next;
            return old.value;
        } finally {
            locked.set(false);
        }

    }

    @Override
    public void push(T newElement) {
        while (!locked.compareAndSet(false, true)) {
            //NOP
        }
        try {
            this.top = new Node<T>(newElement, this.top);
        } finally {
            locked.set(false);
        }
    }

    @Override
    public T peek() {
        while (!locked.compareAndSet(false, true)) {
            //NOP
        }
        try {
            return top.value;
        } finally {
            locked.set(false);
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
