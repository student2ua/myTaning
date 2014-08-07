package com.tor.util.lockExample;

import java.util.concurrent.atomic.AtomicReference;

/**
 * User: tor
 * Date: 07.08.14
 * Time: 14:30
 * неблокирующий стек Трейбера
 */
public class StackTreiber<T> implements Stack<T> {

    private final AtomicReference<Node<T>> top = new AtomicReference<Node<T>>(null);

    @Override
    public T pop() {
        while (true) {
            Node<T> oldT = this.top.get();
            Node<T> newT = oldT.next;
            if (top.compareAndSet(oldT, newT)) {
                return oldT.value;
            }
        }
    }

    @Override
    public void push(T newElement) {
        Node<T> newTop = new Node<T>(newElement, null);
        while (true) {
            Node<T> oldT = top.get();
            newTop.next = oldT;
            if (top.compareAndSet(oldT, newTop)) {
                break;
            }
        }
    }

    @Override
    public T peek() {
        return top.get().value;
    }

    private static class Node<E> {
        private final E value;
        private Node<E> next;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }
}
