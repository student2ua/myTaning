package com.tor.util.lockExample;

/**
 * User: tor
 * Date: 04.08.14
 * Time: 15:05
 * http://habrahabr.ru/post/231953/
 */
public interface Stack<T> {
    /**
     * забрать значение из стека, указатель смещается
     */
    T pop();

    /**
     * положить в стек, сдвинув предидущее значение
     */
    void push(T newElement);

    /**
     * прочитать значение из стека, указатель не смещается
     */
    T peek();
}
