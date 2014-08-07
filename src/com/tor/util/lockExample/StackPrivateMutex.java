package com.tor.util.lockExample;

/**
 * User: tor
 * Date: 04.08.14
 * Time: 17:10
 * http://c2.com/cgi/wiki?PrivateMutex
 */
public class StackPrivateMutex<T> implements Stack<T> {
    private final Object lock = new Object();
    private Node<T> top = null;

    @Override
    public T pop() {
        synchronized (lock) {
            Node<T> old = this.top;
            this.top = this.top.next;
            return old.value;
        }
    }

    @Override
    public void push(T newElement) {
        synchronized (lock) {
            this.top = new Node<T>(newElement, this.top);
        }
    }

    @Override
    public T peek() {
        synchronized (lock) {
            return this.top.value;
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
