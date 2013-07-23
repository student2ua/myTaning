package com.tor.algoritms;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 10.06.13
 * Time: 16:40
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * <p><b>Система непересекающихся множеств</b> позволяет администрировать множество элементов, разбитое на непересекающиеся
 * подмножества. При этом каждому подмножеству назначается его представитель — элемент этого подмножества. Абстрактная
 * структура данных определяется множеством трех операций {Union, Find, MakeSet}.
 * Очень удобна для хранения компонент связности в графах.<p/>
 * <p/>
 * http://en.wikipedia.org/wiki/Disjoint-set_data_structure
 * https://sites.google.com/site/indy256/algo/disjoint_sets
 * http://habrahabr.ru/post/104772/
 */
public class DisjointSets {

    public static int[] createSets(int size) {
        int[] p = new int[size];
        for (int i = 0; i < size; i++)
            p[i] = i;
        return p;
    }

    public static int root(int[] p, int x) {
        return x == p[x] ? x : (p[x] = root(p, p[x]));
    }

    public static void unite(int[] p, int a, int b) {
        a = root(p, a);
        b = root(p, b);
        if (a != b)
            p[a] = b;
    }

    // Usage example
    public  void main(String[] args) {
        int[] p = createSets(10);
        System.out.println(false == (root(p, 0) == root(p, 9)));
        unite(p, 0, 9);
        System.out.println(true == (root(p, 0) == root(p, 9)));
    }
}

