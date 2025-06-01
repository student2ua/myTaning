package com.tor.util.concurrent.cache;

/**
 * User: tor
 * Time: 22:16
 */
public interface Function<K, V> {
    V apply(K key);
}
