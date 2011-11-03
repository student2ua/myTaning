package com.tor.algoritms;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 30.10.2009
 * Time: 15:04:24
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Vladimir Yaroslavskiy
 * @version 2009.09.17 m765.817
 */
public class DualPivotQuicksort817 {
    public static void sort(int[] a) {
        sort(a, 0, a.length);
    }

    public static void sort(int[] a, int fromIndex, int toIndex) {
        rangeCheck(a.length, fromIndex, toIndex);
        dualPivotQuicksort(a, fromIndex, toIndex - 1);
    }

    private static void rangeCheck(int length, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ")> toIndex(" + toIndex + ")");
        }
        if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException(fromIndex);
        }
        if (toIndex > length) {
            throw new ArrayIndexOutOfBoundsException(toIndex);
        }
    }

    private static void dualPivotQuicksort(int[] a, int left, int right) {
        int len = right - left;
        int x;
        if (len < TINY_SIZE) { // insertion sort on tiny array
            for (int i = left + 1; i <= right; i++) {
                for (int j = i; j > left && a[j] < a[j - 1]; j--) {
                    x = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = x;
                }
            }
            return;
        }
// median indexes
        int sixth = len / 6;
        int m1 = left + sixth;
        int m2 = m1 + sixth;
        int m3 = m2 + sixth;
        int m4 = m3 + sixth;
        int m5 = m4 + sixth;
// 5-element sorting network
        if (a[m1] > a[m2]) {
            x = a[m1];
            a[m1] = a[m2];
            a[m2] = x;
        }
        if (a[m4] > a[m5]) {
            x = a[m4];
            a[m4] = a[m5];
            a[m5] = x;
        }
        if (a[m1] > a[m3]) {
            x = a[m1];
            a[m1] = a[m3];
            a[m3] = x;
        }
        if (a[m2] > a[m3]) {
            x = a[m2];
            a[m2] = a[m3];
            a[m3] = x;
        }
        if (a[m1] > a[m4]) {
            x = a[m1];
            a[m1] = a[m4];
            a[m4] = x;
        }
        if (a[m3] > a[m4]) {
            x = a[m3];
            a[m3] = a[m4];
            a[m4] = x;
        }
        if (a[m2] > a[m5]) {
            x = a[m2];
            a[m2] = a[m5];
            a[m5] = x;
        }
        if (a[m2] > a[m3]) {
            x = a[m2];
            a[m2] = a[m3];
            a[m3] = x;
        }
        if (a[m4] > a[m5]) {
            x = a[m4];
            a[m4] = a[m5];
            a[m5] = x;
        }
// pivots: [ < pivot1 | pivot1 <= && <= pivot2 | > pivot2 ]
        int pivot1 = a[m2];
        int pivot2 = a[m4];
        boolean diffPivots = pivot1 != pivot2;
        a[m2] = a[left];
        a[m4] = a[right];
// center part pointers
        int less = left + 1;
        int great = right - 1;
// sorting
        if (diffPivots) {
            for (int k = less; k <= great; k++) {
                x = a[k];
                if (x < pivot1) {
                    a[k] = a[less];
                    a[less++] = x;
                } else if (x > pivot2) {
                    while (a[great] > pivot2 && k < great) {
                        great--;
                    }
                    a[k] = a[great];
                    a[great--] = x;
                    x = a[k];
                    if (x < pivot1) {
                        a[k] = a[less];
                        a[less++] = x;
                    }
                }
            }
        } else {
            for (int k = less; k <= great; k++) {
                x = a[k];
                if (x == pivot1) {
                    continue;
                }
                if (x < pivot1) {
                    a[k] = a[less];
                    a[less++] = x;
                } else {
                    while (a[great] > pivot2 && k < great) {
                        great--;
                    }
                    a[k] = a[great];
                    a[great--] = x;
                    x = a[k];
                    if (x < pivot1) {
                        a[k] = a[less];
                        a[less++] = x;
                    }
                }
            }
        }
// swap
        a[left] = a[less - 1];
        a[less - 1] = pivot1;
        a[right] = a[great + 1];
        a[great + 1] = pivot2;
// left and right parts
        dualPivotQuicksort(a, left, less - 2);
        dualPivotQuicksort(a, great + 2, right);
// equal elements
        if (great - less > len - DIST_SIZE && diffPivots) {
            for (int k = less; k <= great; k++) {
                x = a[k];
                if (x == pivot1) {
                    a[k] = a[less];
                    a[less++] = x;
                } else if (x == pivot2) {
                    a[k] = a[great];
                    a[great--] = x;
                    x = a[k];
                    if (x == pivot1) {
                        a[k] = a[less];
                        a[less++] = x;
                    }
                }
            }
        }
// center part
        if (diffPivots) {
            dualPivotQuicksort(a, less, great);
        }
    }

    private static final int DIST_SIZE = 13;
    private static final int TINY_SIZE = 17;
}