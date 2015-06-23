package com.tor.swing.tree.tableOfContens;

/**
 * User: tor
 * Date: 23.06.15
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class TableOfContents {
    public final String title;
    public final Integer page;

    TableOfContents(String title, Integer page) {
        this.title = title;
        this.page = page;
    }

    @Override
    public String toString() {
        return title;
    }
}
