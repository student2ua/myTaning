package com.tor.algoritms.trees;


/**
 * User: tor
 * Date: 26.12.14
 * Time: 12:47
 */
public class BinarySearchTree<K extends Comparable<K>, V> implements TreeActions<K, V> {
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> root;

    @Override
    public V get(K key) {
        Node<K, V> x = root;
        while (x != null) {
            int cRez = key.compareTo(x.key);
            if (cRez == 0) return x.value;
            if (cRez < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return null;
    }

    @Override
    public void remove(K key) {
        Node<K, V> x = root, y = null;
        while (x != null) {
            int rez = key.compareTo(x.key);
            if (rez == 0) {
                break;
            } else {
                y = x;
                if (rez < 0) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
        }
        if (x == null) return;
        if (x.right == null) {
            if (y == null) {
                root = x.left;
            } else {
                if (x == y.left) {
                    y.left = x.left;
                } else {
                    y.right = x.left;
                }
            }
        } else {
            Node<K, V> lefrM = x.right;
            y = null;
            while (lefrM.left != null) {
                y = lefrM;
                lefrM = lefrM.left;
            }
            if (y != null) {
                y.left = lefrM.right;

            } else {
                x.right = lefrM.right;
            }
            x.key = lefrM.key;
            x.value = lefrM.value;
        }

    }

    @Override
    public void add(K key, V value) {
        Node<K, V> x = root, y = null;
        while (x != null) {
            int rez = key.compareTo(x.key);
            if (rez == 0) {
                x.value = value;
                return;
            } else {
                y = x;
                if (rez < 0) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            Node<K, V> newNode = new Node<K, V>(key, value);
            if (y == null) {
                root = newNode;
            } else if (key.compareTo(y.key) < 0) {
                y.left = newNode;
            } else {
                y.right = newNode;
            }
        }
    }

    public boolean contains(K key) {
        Node<K, V> x = root;
        while (x != null) {
            int cRez = key.compareTo(x.key);
            if (cRez == 0) return true;
            if (cRez < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return false;
    }
}
