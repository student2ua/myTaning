package com.tor.util.lockExample;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * User: tor
 * Date: 04.08.14
 * Time: 15:02
 * Если вы полностью контролируете использование вашего стека, то может оказаться, что
 * 1) его передача между потоками всегда сопряжена с happens-before ребром
 * 2) логика приложения такова, что потоки используют (читают/пишут) стек «по очереди»
 * И передаем через happens-before ребро образованное вызовом метода start() и первой инструкцией метода run()
 */
public class StackTrivial<T> implements Stack<T> {
    private Node<T> top = null;

    @Override
    public T pop() {
        Node<T> oldTop = this.top;
        this.top = this.top.next;
        return oldTop.value;
    }

    @Override
    public void push(T element) {
        this.top = new Node<T>(element, this.top);
    }

    @Override
    public T peek() {
        return top.value;
    }

    private static class Node<E> {
        private final E value;
        private final Node<E> next;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        var1();

        var2();

    }

    private static void var1() {
        final Stack<Integer> stack = new StackTrivial<Integer>();
        // меняем стек в потоке main
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        new Thread(new Runnable() {
            public void run() {
                // меняем стек в другом потоке
                stack.pop();
                // и читаем, это все не проблема
                System.out.println("v1 "+stack.pop());
            }
        }).start();

     /* data-racefull гореть в аду
       new Thread(new Runnable() {
            public void run() {
                System.out.println(stack.peek());
            }
        }).start();*/
    }

    /**
     * передача через потокозащищенные коллекции
     */
    private static void var2() {
        final Stack<Integer> stack = new StackTrivial<Integer>();
        final BlockingQueue<Stack<Integer>> iThreadQueue = new LinkedBlockingQueue<Stack<Integer>>();
        // меняем стек в потоке main
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        new Thread(new Runnable() {
            public void run() {
                stack.push(100);
                try {
                    iThreadQueue.put(stack);
                } catch (InterruptedException ignore) {
                }
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                Stack<Integer> stack = null;
                try {
                    stack = iThreadQueue.take();
                    System.out.println("v2 "+stack.pop());
                } catch (InterruptedException ignore) {
                }

            }
        }).start();
    }


}
