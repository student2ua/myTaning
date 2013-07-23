package com.tor.algoritms;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 10.06.13
 * Time: 17:13
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * https://sites.google.com/site/indy256/algo/graph
 */
public interface Graph {
    public void addNode(Object node);

    public void removeNode(Object node);

    public void addEdge(Object node1, Object node2);
}

class GraphImp implements Graph {
    public Map<Object, Set> edges = new TreeMap();

    @Override
    public void addNode(Object node) {
        if (!edges.containsKey(node))
            edges.put(node, new TreeSet());
    }

    @Override
    public void removeNode(Object node) {
        if (edges.containsKey(node)) {
            for (Iterator it = edges.get(node).iterator(); it.hasNext(); ) {
                edges.get(it).remove(node);
            }
            edges.remove(node);
        }
    }

    @Override
    public void addEdge(Object node1, Object node2) {
        addNode(node1);
        addNode(node2);
        edges.get(node1).add(node2);
        edges.get(node2).add(node1);
    }

    @Test
    public void testGraph() {
        Graph g = new GraphImp();
        Integer node2 = new Integer(2);
        g.addEdge(new Integer(1), node2);
        Assert.assertTrue(edges.size() == 2);
        g.removeNode(node2);
    }
}
