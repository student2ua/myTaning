package com.tor.algoritms.subsequence;

import org.apache.commons.lang.enum.ValuedEnum;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.04.2010
 * Time: 17:27:39
 *
 * @see http://en.wikibooks.org/wiki/Algorithm_implementation/Strings/Longest_common_subsequence
 */
public abstract class LongestCommonSubsequence {
    private static final Logger log = Logger.getLogger(LongestCommonSubsequence.class);
    private int[][] c;
    private ArrayList diff; //<DiffEntry<VALUE>>
    private ArrayList backtrack;  //<VALUE>


    /**
     * A constructor for classes inheriting this one, allowing them to
     * do some initialization before setting the values of X and Y.  Once
     * the initialization is complete, the inheriting class must call
     * initValues(VALUE[] x, VALUE[] y)
     */
    protected LongestCommonSubsequence() {

    }

    protected abstract int lengthOfY();

    protected abstract int lengthOfX();

    protected abstract Object valueOfX(int index);

    protected abstract Object valueOfY(int index);

    protected boolean equals(Object x1, Object y1) {
        return (null == x1 && null == y1) || x1.equals(y1);
    }


    private boolean isXYEqual(int i, int j) {
        return equals(valueOfXInternal(i), valueOfYInternal(j));
    }

    private Object valueOfXInternal(int i) {
        return valueOfX(i - 1);
    }

    private Object valueOfYInternal(int j) {
        return valueOfY(j - 1);
    }

    public void calculateLcs() {
        if (c != null) {
            return;
        }
        c = new int[lengthOfX() + 1][];
        for (int i = 0; i < c.length; i++) {
            c[i] = new int[lengthOfY() + 1];
        }

        for (int i = 1; i < c.length; i++) {
            for (int j = 1; j < c[i].length; j++) {
                if (isXYEqual(i, j)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else {
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }
    }

    public int getLcsLength() {
        calculateLcs();

        return c[lengthOfX()][lengthOfY()];
    }

    public int getMinEditDistance() {
        calculateLcs();
        return lengthOfX() + lengthOfY() - 2 * Math.abs(getLcsLength());
    }

    public List backtrack() {
        calculateLcs();
        if (this.backtrack == null) {
            this.backtrack = new ArrayList();
            backtrack(lengthOfX(), lengthOfY());
        }
        return this.backtrack;
    }

    public void backtrack(int i, int j) {
        calculateLcs();

        if (i == 0 || j == 0) {
            return;
        } else if (isXYEqual(i, j)) {
            backtrack(i - 1, j - 1);
            backtrack.add(valueOfXInternal(i));
        } else {
            if (c[i][j - 1] > c[i - 1][j]) {
                backtrack(i, j - 1);
            } else {
                backtrack(i - 1, j);
            }
        }
    }

    public List diff() {
        calculateLcs();

        if (this.diff == null) {
            this.diff = new ArrayList();
            diff(lengthOfX(), lengthOfY());
        }
        return this.diff;
    }

    private void diff(int i, int j) {
        calculateLcs();

        while (!(i == 0 && j == 0)) {
            if (i > 0 && j > 0 && isXYEqual(i, j)) {
                this.diff.add(new DiffEntry(DiffType.NONE, valueOfXInternal(i)));
                i--;
                j--;

            } else {
                if (j > 0 && (i == 0 || c[i][j - 1] >= c[i - 1][j])) {
                    this.diff.add(new DiffEntry(DiffType.ADD, valueOfYInternal(j)));
                    j--;

                } else if (i > 0 && (j == 0 || c[i][j - 1] < c[i - 1][j])) {

                    this.diff.add(new DiffEntry(DiffType.REMOVE, valueOfXInternal(i)));
                    i--;
                }
            }
        }

        Collections.reverse(this.diff);
    }

    //@Override
    public String toString() {
        calculateLcs();

        StringBuffer buf = new StringBuffer();
        buf.append("  ");
        for (int j = 1; j <= lengthOfY(); j++) {
            buf.append(valueOfYInternal(j));
        }
        buf.append("\n");
        buf.append(" ");
        for (int j = 0; j < c[0].length; j++) {
            buf.append(Integer.toString(c[0][j]));
        }
        buf.append("\n");
        for (int i = 1; i < c.length; i++) {
            buf.append(valueOfXInternal(i));
            for (int j = 0; j < c[i].length; j++) {
                buf.append(Integer.toString(c[i][j]));
            }
            buf.append("\n");
        }
        return buf.toString();
    }



    public static class DiffEntry {
        private DiffType type;
        private Object value;

        public DiffEntry(DiffType type, Object value) {
            super();
            this.type = type;
            this.value = value;
        }

        public DiffType getType() {
            return type;
        }

        public void setType(DiffType type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

     //   @Override
        public String toString() {
            return type.toString() + value.toString();
        }

    }
}
