package com.tor.algoritms.trees;

/**
 * User: tor
 * Date: 26.12.14
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface TreeActions<K, V> {
    /**
     * Получить значение из дерева по ключу
     */
    V get(K key);

    /**
     * Удалить значение из дерева
     */
    void remove(K key);

    void add(K key, V value);

    boolean contains(K key);
}
