package com.tor.util.lockExample;

/**
 * User: tor
 * Date: 04.08.14
 * Time: 16:49
 * Так не Делать!
 * мы синхронизируемся по встроенному монитору стека, который «виден всем», кто видит стек. Таким образом,
 * мы открываем наружу детали реализации синхронизации (нарушение инкапсуляции).
 */
public class StackSynchronized<T> implements Stack<T> {
    private Node<T> top = null;

    @Override
    public synchronized T pop() {
        Node<T> old = this.top;
        this.top = this.top.naxt;
        return old.value;
    }

    @Override
    public synchronized void push(T newElement) {
        this.top = new Node<T>(newElement, this.top);
    }

    @Override
    public T peek() {
        return top.value;
    }

    private static class Node<E> {
        private final E value;
        private final Node<E> naxt;

        private Node(E value, Node<E> naxt) {
            this.value = value;
            this.naxt = naxt;
        }
    }

    public static void main(String[] args) {
        final Stack<String> stack = new StackSynchronized<String>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    stack.push("a");
                    stack.pop();
                    System.out.println("stack push/pop");
                }
            }
        }).start();
        // поток-паразит
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (stack) {
                    while (true) ;
                }
            }
        }).start();
        //Поток-паразит использовал встроенный стек и «повис», повесив операции со стеком
    }
}
