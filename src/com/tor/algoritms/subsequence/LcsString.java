package com.tor.algoritms.subsequence;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.04.2010
 * Time: 18:17:10
 * To change this template use File | Settings | File Templates.
 */
public class LcsString extends LongestCommonSubsequence {
    private static final Logger log = Logger.getLogger(LcsString.class);

    private String x;
    private String y;

    public LcsString(String from, String to) {
        this.x = from;
        this.y = to;
    }

    protected int lengthOfY() {
        return y.length();
    }

    protected int lengthOfX() {
        return x.length();
    }

    protected Object valueOfX(int index) {
        return new Character(x.charAt(index));
    }

    protected Object valueOfY(int index) {
        return new Character(y.charAt(index));

    }

    public String getHtmlDiff() {
        DiffType type = null;
        List diffs = diff();
        StringBuffer buf = new StringBuffer();

        for (Iterator it = diffs.iterator(); it.hasNext();) {
            DiffEntry entry = (DiffEntry) it.next();
            if (type != entry.getType()) {
                if (type != null) {
                    buf.append("</span>");
                }
                buf.append("<span class=\"" + entry.getType().getName() + "\">");
                type = entry.getType();
            }
            buf.append(escapeHtml(((Character) entry.getValue()).charValue()));
        }
        buf.append("</span>");
        return buf.toString();
    }

    private String escapeHtml(char ch) {
        switch (ch) {
            case '<':
                return "&lt;";
            case '>':
                return "&gt;";
            case '"':
                return "&quot;";
            default:
                return new Character(ch).toString();
        }
    }

    // EXAMPLE.  Here's how you use it.
    public static void main(String[] args) {
        LcsString seq = new LcsString("<p>the quick brown fox</p>", "<p>the <b>Fast</b> brown dog</p>");
        System.out.println("LCS: " + seq.getLcsLength());
        System.out.println("Edit Dist: " + seq.getMinEditDistance());
        System.out.println("Backtrack: " + seq.backtrack());
        System.out.println("HTML Diff: " + seq.getHtmlDiff());

    }
}
